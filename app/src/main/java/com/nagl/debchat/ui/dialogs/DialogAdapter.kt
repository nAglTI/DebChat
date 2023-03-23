package com.nagl.debchat.ui.dialogs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nagl.debchat.data.model.Dialog
import com.nagl.debchat.data.model.Message
import com.nagl.debchat.databinding.DialogItemBinding

class DialogAdapter(private val delegate: OnItemClickListener) : ListAdapter<Dialog, DialogAdapter.DialogViewHolder>(DialogDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DialogViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DialogItemBinding.inflate(layoutInflater, parent, false)
        return DialogViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DialogViewHolder, position: Int) {
        val dialog = getItem(position)
        if (dialog != null) {
            holder.itemView.setOnClickListener {
                delegate.onDialogClick(dialog)
            }
            holder.bind(dialog)
        }
    }

    class DialogViewHolder(private val binding: DialogItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(dialog: Dialog) {
            binding.dialog = dialog
//            binding.dialogLayout.setOnClickListener {
//                val intent = Intent(context, ChatActivity::class.java)
//                intent.apply {
//                    putExtra(CHAT_ID_ARGS, dialog.chatId)
//                    putExtra("DialogTitle", dialog.title)
//                    putExtra("DialogUsers", ArrayList(dialog.users))
//                }
//                context.startActivity(intent)
//            }
            binding.executePendingBindings()
        }
    }

    /**
     *  A utility class [DiffUtil] that helps to calculate updates for a [RecyclerView] Adapter.
     */
    class DialogDiffCallBack : DiffUtil.ItemCallback<Dialog>() {
        override fun areItemsTheSame(oldItem: Dialog, newItem: Dialog): Boolean {
            return oldItem.chatId == newItem.chatId
        }

        override fun areContentsTheSame(
            oldItem: Dialog,
            newItem: Dialog
        ): Boolean {
            return oldItem == newItem
        }
    }

    interface OnItemClickListener{
        fun onDialogClick(dialog: Dialog)
    }

    companion object {
        const val f = "Message()"
    }
}