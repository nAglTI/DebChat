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
        messageAdapter.submitList(chatViewModel.messagesList)
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
            override fun onLoadMore(firstNewItemId: Int) {
                chatViewModel.getNetMessages(
                    (binding.dialog as Dialog).chatId,
                    messageAdapter.currentList[if (firstNewItemId == 0) 0 else firstNewItemId - 1].id // TODO: изменить id верхней границы
                )
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeViewModel() {
        chatViewModel.newMessages.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                if (chatViewModel.messagesList.isEmpty()) {
                    chatViewModel.messagesList.addAll(it)
                    messageAdapter.submitList(it)
                } else {
                    if (chatViewModel.messagesList[0].id > it[0].id) {
                        val newList = mutableListOf<Message>()
                        newList.addAll(it)
                        newList.addAll(chatViewModel.messagesList)
                        chatViewModel.messagesList.apply {
                            clear()
                            addAll(newList)
                        }
                        messageAdapter.submitList(newList)
                    } else {
                        chatViewModel.messagesList.addAll(it)
                        messageAdapter.submitList(chatViewModel.messagesList)
                    }
                }
                //messageAdapter.submitList(chatViewModel.messagesList)
            } else {
                // TODO: toast or snack or nothing
            }
        }
    }

    companion object {
        const val CHAT_ARGS = "bundle_chat_args"
    }
}