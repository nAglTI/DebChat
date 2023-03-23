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
import com.nagl.debchat.data.model.User
import com.nagl.debchat.databinding.FragmentChatBinding
import com.nagl.debchat.ui.BaseFragment
import com.nagl.debchat.utils.parcelable


class ChatFragment : BaseFragment() {

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!
    private val chatViewModel by viewModels<ChatViewModel> { viewModelFactoryProvider }

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
        chatViewModel.getCacheMessages((binding.dialog as Dialog).chatId)
        setListeners()
    }

    private fun setListeners() {
        binding.sendButton.setOnClickListener {
            // chatViewModel.send
        }

        val mLayoutManager = LinearLayoutManager(requireContext())
        binding.messagesListRecyclerView.layoutManager = mLayoutManager

        //var loading = true
        var pastVisibleItems: Int
        var visibleItemCount: Int
        var totalItemCount: Int
        binding.messagesListRecyclerView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy < 0) { // scroll up todo
                    visibleItemCount = mLayoutManager.childCount
                    totalItemCount = mLayoutManager.itemCount
                    pastVisibleItems = mLayoutManager.findLastVisibleItemPosition()
                    if ((visibleItemCount + pastVisibleItems) <= totalItemCount) {
                        //loading = false;
                        Log.v("...", "Last Item Wow !");
                        // Do pagination.. i.e. fetch new data

                        //loading = true;
                    }
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeViewModel() {
        chatViewModel.messages.observe(viewLifecycleOwner) {
            // TODO: check cached mess list with it & check isEmpty()
            //if ()
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