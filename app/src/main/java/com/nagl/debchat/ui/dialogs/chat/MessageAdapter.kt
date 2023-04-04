package com.nagl.debchat.ui.dialogs.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nagl.debchat.data.model.Message
import com.nagl.debchat.data.model.User
import com.nagl.debchat.databinding.MessageItemBinding

class MessageAdapter(private val currUser: User) : ListAdapter<Message, MessageAdapter.MessageViewHolder>(MessageDiffCallBack()) {

    private var onLoadMoreListener: OnLoadMoreListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MessageItemBinding.inflate(layoutInflater, parent, false)
        return MessageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = getItem(position)
        if (message != null) {
            holder.bind(message, currUser.name, message.senderUserId == currUser.userId)
        }

        if (position == 50) { // TODO: проверить границу загрузки сообщений
            onLoadMoreListener?.onLoadMore(0)
        } else if (position == itemCount - 50) {
            onLoadMoreListener?.onLoadMore(itemCount + 1)
        }
    }

    class MessageViewHolder(private val binding: MessageItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message, userName: String, isOwner: Boolean) {
            binding.message = message
            binding.userName = userName
            binding.isOwner = isOwner
            binding.executePendingBindings()
        }
    }

    interface OnLoadMoreListener {
        fun onLoadMore(firstNewItemId: Int)
    }

    fun setOnLoadMoreListener(onLoadMoreListener: OnLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener
    }

    /**
     *  A utility class [DiffUtil] that helps to calculate updates for a [RecyclerView] Adapter.
     */
    class MessageDiffCallBack : DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Message,
            newItem: Message
        ): Boolean {
            return oldItem == newItem
        }
    }
}