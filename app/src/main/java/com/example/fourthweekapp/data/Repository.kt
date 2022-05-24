package com.example.fourthweekapp.data

import com.example.fourthweekapp.data.models.ChatItem
import com.github.javafaker.Faker

class Repository {
    private val faker = Faker()
    private val chatList = ArrayList<ChatItem>()

    private fun getChatItem(): ChatItem {
        val name = faker.rickAndMorty().character()
        return ChatItem(
            name = name,
            icon = name.first().uppercaseChar(),
            message = faker.rickAndMorty().quote(),
            iconColor = faker.color().hex(),
            date = faker.date().birthday(),
            unreadMessage = 0,
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
                val messages = oldItem.unreadMessage
                val newItem = oldItem.copy(
                    unreadMessage = faker.number().numberBetween(messages, messages + 5),
                    message = faker.rickAndMorty().quote(),
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