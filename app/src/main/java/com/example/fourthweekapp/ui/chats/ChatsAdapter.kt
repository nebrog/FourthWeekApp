package com.example.fourthweekapp.ui.chats

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.fourthweekapp.R
import com.example.fourthweekapp.data.models.ChatItem
import java.text.SimpleDateFormat

class ChatsAdapter(private val onChatClickListener: OnChatClickListener) :
    RecyclerView.Adapter<ChatsAdapter.BasicViewHolder>() {
    private val CHATS = 1
    private val PROGRESS = 2
    private var chatsList: List<ChatItem> = ArrayList()

    fun setChats(chats: List<ChatItem>) {
        chatsList = chats
        notifyDataSetChanged()

    }

    fun addNewChats(newChats: List<ChatItem>) {
        chatsList = chatsList + newChats
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasicViewHolder {
        if (viewType == CHATS) {
            val item =
                LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
            return ChatItemViewHolder(item)
        } else {
            val item = LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_item_progress_bar, parent, false)
            return ProgressbarViewHolder(item)
        }
    }

    override fun onBindViewHolder(holder: BasicViewHolder, position: Int) {
        when (holder) {
            is ChatItemViewHolder -> {
                val chatItem = chatsList[position]
                holder.bindData(chatItem)
                holder.itemView.setOnClickListener(View.OnClickListener {
                    onChatClickListener.onChatItemClick(chatItem)
                }
                )
            }
            is ProgressbarViewHolder -> {
                //no-op
            }
        }
    }

    override fun getItemCount(): Int {
        return chatsList.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        if (position == chatsList.size) {
            return PROGRESS
        } else {
            return CHATS
        }
    }

    sealed class BasicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class ChatItemViewHolder(itemView: View) : BasicViewHolder(itemView) {


        @SuppressLint("SimpleDateFormat")
        private val timeFormatter = SimpleDateFormat("HH:mm")

        private val avatarBackground = itemView.findViewById<CardView>(R.id.avatar_container)
        private val avatar = itemView.findViewById<TextView>(R.id.avatar_letter)
        private val chatName = itemView.findViewById<TextView>(R.id.chat_name)
        private val chatText = itemView.findViewById<TextView>(R.id.chat_text)
        private val date = itemView.findViewById<TextView>(R.id.message_date)
        private val unreadNumber = itemView.findViewById<TextView>(R.id.unread_number)

        fun bindData(item: ChatItem) {
            avatar.text = item.icon.toString()
            avatarBackground.setCardBackgroundColor(Color.parseColor(item.iconColor))
            chatName.text = item.name
            chatText.text = item.messages.get(item.messages.size-1).message
            date.text = timeFormatter.format(item.date)
            unreadNumber.isVisible = item.unreadMessage != 0
            unreadNumber.text = item.unreadMessage.toString()
        }
    }

    class ProgressbarViewHolder(itemView: View) : BasicViewHolder(itemView)
}