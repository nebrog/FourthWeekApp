package com.example.fourthweekapp.ui.communication

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.fourthweekapp.R
import com.example.fourthweekapp.data.models.MessageItem
import java.text.SimpleDateFormat

class CommunicationAdapter : RecyclerView.Adapter<CommunicationAdapter.BasicViewHolder>() {
    private val SENDER = 1
    private val USER = 2
    private var messageList: List<MessageItem> = ArrayList()


    fun setMessages(messages: List<MessageItem>) {
        val previousList = messageList
        messageList = messages
        val minSize = Math.min(previousList.size, messages.size)
        for (i in 0 until minSize) {
            if (messages[i] != previousList[i]) {
                notifyItemChanged(i)
            }
        }
        if (messages.size > previousList.size) {
            notifyItemRangeInserted(previousList.size, messages.size - previousList.size)
        }

    }

    fun addNewMessages(newMessages: List<MessageItem>) {
        val previousSize = messageList.size
        messageList = messageList + newMessages
        if (newMessages.size > 0) {
            notifyItemRangeInserted(previousSize, newMessages.size)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasicViewHolder {
        if (viewType == SENDER) {
            val item = LayoutInflater.from(parent.context)
                .inflate(R.layout.chat_sender_item, parent, false)
            return SenderViewHolder(item)
        } else {
            val item = LayoutInflater.from(parent.context)
                .inflate(R.layout.chat_user_item, parent, false)
            return UserViewHolder(item)
        }

    }

    override fun onBindViewHolder(holder: BasicViewHolder, position: Int) {
        val messageItem = messageList.get(position)
        when (holder) {

            is SenderViewHolder -> {
                holder.bindData(messageItem)

            }
            is UserViewHolder -> {

                holder.bindData(messageItem)
            }
        }

    }

    override fun getItemCount(): Int {
        return messageList.size

    }

    override fun getItemViewType(position: Int): Int {
        if (messageList.get(position).sender) {
            return SENDER
        } else {
            return USER
        }
    }

    sealed class BasicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    class SenderViewHolder(itemView: View) : BasicViewHolder(itemView) {
        @SuppressLint("SimpleDateFormat")
        private val timeFormatter = SimpleDateFormat("HH:mm")
        private val senderText = itemView.findViewById<TextView>(R.id.sender_text)
        private val senderTime = itemView.findViewById<TextView>(R.id.sender_time)
        private val senderEdited = itemView.findViewById<TextView>(R.id.sender_edited)


        fun bindData(item: MessageItem) {
            senderText.text = item.message
            if (item.wasDeleted) {
                senderText.paintFlags = senderText.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                senderText.paintFlags = senderText.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
            senderTime.text = timeFormatter.format(item.date)
            senderEdited.isVisible = item.hasBeenEdited

        }
    }

    class UserViewHolder(itemView: View) : BasicViewHolder(itemView) {
        @SuppressLint("SimpleDateFormat")
        private val timeFormatter = SimpleDateFormat("HH:mm")
        private val userText = itemView.findViewById<TextView>(R.id.user_text)
        private val userTime = itemView.findViewById<TextView>(R.id.user_time)
        private val userEdited = itemView.findViewById<TextView>(R.id.user_edited)

        fun bindData(item: MessageItem) {
            userText.text = item.message
            if (item.wasDeleted) {
                userText.paintFlags = userText.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                userText.paintFlags = userText.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
            userTime.text = timeFormatter.format(item.date)
            userEdited.isVisible = item.hasBeenEdited

        }
    }

}