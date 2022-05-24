package com.example.fourthweekapp

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.fourthweekapp.data.models.ChatItem
import java.text.SimpleDateFormat

class Adapter(private val onChatClickListener: OnChatClickListener) :
    RecyclerView.Adapter<Adapter.ChatItemViewHolder>() {
    private var chatsList: List<ChatItem> = ArrayList()

    fun setChats(chats: List<ChatItem>) {
        chatsList = chats
        notifyDataSetChanged()

    }

    fun addNewChats(newChats: List<ChatItem>) {
        chatsList = chatsList + newChats
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatItemViewHolder {
        val item =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        return ChatItemViewHolder(item)
    }

    override fun onBindViewHolder(holder: ChatItemViewHolder, position: Int) {
        val chatItem = chatsList[position]
        holder.bindData(chatItem)
        holder.itemView.setOnClickListener(View.OnClickListener {
            onChatClickListener.onChatItemClick(chatItem)
        }

        )
    }

    override fun getItemCount(): Int {
        return chatsList.size
    }

    class ChatItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

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
            chatText.text = item.message
            date.text = timeFormatter.format(item.date)
            unreadNumber.isVisible = item.unreadMessage != 0
            unreadNumber.text = item.unreadMessage.toString()
        }
    }

}