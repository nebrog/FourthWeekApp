package com.example.fourthweekapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ChatWindow : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_window)
//        val name = intent.getStringExtra("message")
//        val chat = findViewById<TextView>(R.id.chat_text)
//        chat.text = name
    }
}