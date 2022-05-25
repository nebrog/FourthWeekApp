package com.example.fourthweekapp.ui.communication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fourthweekapp.R
import com.example.fourthweekapp.data.Repository
import com.example.fourthweekapp.ui.communication.CommunicationAdapter

class ChatActivity : AppCompatActivity() {
    private val adapter = CommunicationAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_window)
        supportActionBar?.hide()
        val id = intent.getIntExtra("id",0)
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_chat)
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
        adapter.setMessages(Repository.findMessageById(id))
    }
}