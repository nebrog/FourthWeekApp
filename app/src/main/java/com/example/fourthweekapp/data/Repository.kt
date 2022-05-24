package com.example.fourthweekapp.data

import com.example.fourthweekapp.data.models.ChatItem
import com.example.fourthweekapp.data.models.MessageItem
import com.github.javafaker.Faker

class Repository {
    private val faker = Faker()
    private val chatList = ArrayList<ChatItem>()
    private var id = 0

    private fun getMessageItem(): MessageItem{
        return MessageItem(
            message = faker.rickAndMorty().quote(),
            date = faker.date().birthday(),
            sender = false,
            hasBeenEdited = false,
            wasDeleted = false,
        )
    }

    private fun getChatItem(): ChatItem {
        val name = faker.rickAndMorty().character()
        val messages:MutableList<MessageItem> = ArrayList()
        for(i in 1..10){
            val item = getMessageItem()
            messages.add(item)
        }

        return ChatItem(
            name = name,
            icon = name.first().uppercaseChar(),
            messages = messages,
            iconColor = faker.color().hex(),
            date = faker.date().birthday(),
            unreadMessage = 0,
            id = id++

        )
    }

    fun getRandomChatList(offset: Int, count: Int): List<ChatItem> {
        val diff = (offset + count) - chatList.size
        var i = 0
        while (diff > i) {
            val items = getChatItem()
            chatList.add(items)
            i++
        }
        return chatList.subList(offset, offset + count)
    }

    private fun updateExistingElements() {
        for (i in 0 until chatList.size) {
            if (faker.bool().bool() && faker.bool().bool()) {
                val oldItem = chatList[i]
                val oldUnread = oldItem.unreadMessage
                val messages:MutableList<MessageItem> = ArrayList()
                for(i in 1..10){
                    val item = getMessageItem()
                    messages.add(item)
                }
                val newItem = oldItem.copy(
                    unreadMessage = faker.number().numberBetween(oldUnread, oldUnread + 5),
                    messages = messages,
                )
                chatList[i] = newItem
            }
        }

    }

    fun updateChatsList(): List<ChatItem> {
        updateExistingElements()
        chatList.sortByDescending { it.unreadMessage }
        return chatList
    }

}