package com.example.fourthweekapp.data

import com.example.fourthweekapp.data.models.ChatItem
import com.example.fourthweekapp.data.models.MessageItem
import com.github.javafaker.Faker

object Repository {


    private val faker = Faker()
    private var chatList = ArrayList<ChatItem>()
    private var id = 0
    private var messageId = 0

    private fun getMessageItem(): MessageItem {
        return MessageItem(
            message = faker.rickAndMorty().quote(),
            date = faker.date().birthday(),
            sender = faker.bool().bool(),
            hasBeenEdited = false,
            wasDeleted = false,
            id = messageId++,

            )
    }

    private fun getChatItem(): ChatItem {
        val name = faker.rickAndMorty().character()
        val messages: MutableList<MessageItem> = ArrayList()
        for (i in 1..20) {
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

    fun findMessageById(id: Int): ChatItem {
        var iter = 0
        while (chatList.get(iter).id != id) {
            iter++
        }
        return chatList.get(iter)

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
                val messages: MutableList<MessageItem> = ArrayList()
                messages.addAll(oldItem.messages)
                for (i in 1..10) {
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

    fun randomizeChat(chatId: Int): ChatItem {
        val chatIndex = chatIndexById(chatId)
        val chatItem = chatList.get(chatIndex)

        val messageList = ArrayList(chatItem.messages)
        for (i in messageList.indices) {
            val oldMessage = messageList[i]
            val isEdited = faker.bool().bool()
            val newText = if (isEdited) {
                faker.witcher().quote()
            } else {
                oldMessage.message
            }
            val newMessage = oldMessage.copy(
                message = newText,
                hasBeenEdited = isEdited,
                wasDeleted = faker.bool().bool() && faker.bool().bool(),
            )
            messageList[i] = newMessage
        }

        val newChatItem = chatItem.copy(
            messages = messageList
        )
        chatList.set(chatIndex, newChatItem)

        return newChatItem
    }

    fun getRandomMessages(offset: Int, count: Int, id: Int): List<MessageItem> {
        val chatIndex = chatIndexById(id)
        val chatItem = chatList.get(chatIndex)

        val newMessages = ArrayList(chatItem.messages)
        val diff = (offset + count) - newMessages.size
        var i = 0
        while (diff > i) {
            val item = getMessageItem()
            newMessages.add(item)
            i++
        }

        val newChatItem = chatItem.copy(messages = newMessages)
        chatList.set(chatIndex, newChatItem)

        return newMessages.subList(offset, offset + count)

    }

    private fun chatIndexById(chatId: Int): Int {
        for (i in chatList.indices) {
            if (chatList.get(i).id == chatId) {
                return i
            }
        }
        throw IllegalStateException("Не найден чат с id=$chatId")
    }

    fun updateChatsList(): List<ChatItem> {
        updateExistingElements()
        chatList.sortByDescending { it.unreadMessage }
        return chatList
    }

}