package com.nagl.debchat.ui.dialogs.chat

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nagl.debchat.data.model.Dialog
import com.nagl.debchat.data.model.Message
import com.nagl.debchat.data.model.User
import com.nagl.debchat.databinding.FragmentChatBinding
import com.nagl.debchat.ui.BaseFragment
import com.nagl.debchat.ui.dialogs.DialogAdapter
import com.nagl.debchat.utils.parcelable

class ChatFragment : BaseFragment() {

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!
    private val chatViewModel by viewModels<ChatViewModel> { viewModelFactoryProvider }

    private val messageAdapter by lazy { MessageAdapter(User(1, "")) } // todo getActivity().getUser()
    private val messagesList: ArrayList<Message> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatBinding.inflate(inflater)
        observeViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dialog = requireArguments().parcelable<Dialog>(CHAT_ARGS)!!
        chatViewModel.setDialog(dialog)
        binding.dialog = dialog
        binding.messagesListRecyclerView.adapter = messageAdapter
        chatViewModel.getCacheMessages((binding.dialog as Dialog).chatId) // TODO: mb save chatid in viewmodel
        setListeners()
    }

    override fun onSaveInstanceState(outState: Bundle) { // TODO: save ui state
        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
    }

    private fun setListeners() {
        binding.sendButton.setOnClickListener {
            chatViewModel.sendMessage(binding.messageField.text.toString().trim())
        }

        messageAdapter.setOnLoadMoreListener(object : MessageAdapter.OnLoadMoreListener {
            override fun onLoadMore(firstNewItemId: Int) { // todo save itemId for observe call
                chatViewModel.getNetMessages(
                    (binding.dialog as Dialog).chatId,
                    messageAdapter.currentList[if (firstNewItemId == 0) 0 else firstNewItemId - 1].id
                )
            }
        })

//        val mLayoutManager = LinearLayoutManager(requireContext())
//        binding.messagesListRecyclerView.layoutManager = mLayoutManager
        //var loading = true
//        var pastVisibleItems: Int
//        var visibleItemCount: Int
//        var totalItemCount: Int
//        binding.messagesListRecyclerView.addOnScrollListener(object :
//            RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//                if (dy < 0) { // scroll up todo
//                    visibleItemCount = mLayoutManager.childCount
//                    totalItemCount = mLayoutManager.itemCount
//                    pastVisibleItems = mLayoutManager.findLastVisibleItemPosition()
//                    if ((visibleItemCount + pastVisibleItems) <= totalItemCount) {
//                        //loading = false;
//                        Log.v("...", "Last Item Wow !")
//                        // Do pagination.. i.e. fetch new data
//
//                        //loading = true;
//                    }
//                }
//            }
//        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeViewModel() {
        chatViewModel.messages.observe(viewLifecycleOwner) {
            // TODO: check onScrollRecycler update
            if (!it.isNullOrEmpty()) {
                messageAdapter.submitList(it)
            } else {

            }
        }

//        chatViewModel.userToken.observe(viewLifecycleOwner) {
//            it?.let {
//                binding.userToken = it
//            }
//        }
    }

    companion object {
        const val CHAT_ARGS = "bundle_chat_args"
    }
}