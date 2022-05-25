package com.example.fourthweekapp.ui.communication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.fourthweekapp.R
import com.example.fourthweekapp.data.Repository
import com.example.fourthweekapp.ui.chats.ChatsActivity
import com.example.fourthweekapp.ui.communication.CommunicationAdapter

class CommunicationActivity : AppCompatActivity() {
    companion object {
        private const val PAGE_SIZE_CHATS = 10
        private const val PAGE_LOADING_DURATION_MS = 2_000L
    }

    private val adapter = CommunicationAdapter()
    private val id by lazy { intent.getIntExtra("id",0) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_window)
        supportActionBar?.hide()
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_chat)
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
        adapter.setMessages(Repository.findMessageById(id).messages)
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            private var isLoading = false

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                val totalItemIndex = layoutManager.itemCount - 1
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                if (!isLoading && lastVisibleItem == totalItemIndex) {
                    isLoading = true
                    val offset = adapter.itemCount
                    recyclerView.postDelayed({
                        val newMessages = Repository.getRandomMessages(offset,
                            PAGE_SIZE_CHATS,id
                        )
                        adapter.addNewMessages(newMessages)
                        isLoading = false
                    }, PAGE_LOADING_DURATION_MS)
                }
            }
        })
        initSwipeRefresh()
    }
    private fun initSwipeRefresh() {
        val swipeToRefresh = findViewById<SwipeRefreshLayout>(R.id.chat_swipe_to_refresh)
        swipeToRefresh.setOnRefreshListener {
            swipeToRefresh.postDelayed({
                adapter.setMessages(Repository.randomizeChat(id).messages)
                swipeToRefresh.isRefreshing = false
            }, PAGE_LOADING_DURATION_MS)
        }
    }
}