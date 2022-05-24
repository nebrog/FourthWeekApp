package com.example.fourthweekapp.data.models

import java.util.*

data class ChatItem(
    val name: String,
    val icon: Char,
    val messages: List<MessageItem>,
    val date: Date,
    val unreadMessage: Int,
    val iconColor: String,
    val id: Int,
)

