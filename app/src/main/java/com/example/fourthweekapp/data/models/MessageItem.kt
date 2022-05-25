package com.example.fourthweekapp.data.models

import java.util.*

data class MessageItem (
    val message: String,
    val date: Date,
    val sender: Boolean,
    val hasBeenEdited: Boolean,
    val wasDeleted: Boolean,
    val id: Int,

)