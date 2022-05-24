package com.example.fourthweekapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.fourthweekapp.data.Repository
import com.example.fourthweekapp.data.models.ChatItem

class MainActivity : AppCompatActivity(), OnChatClickListener {

    companion object {
        private const val PAGE_SIZE_CHATS = 10
        private const val PAGE_LOADING_DURATION_MS = 2_000L
    }

    private val repository = Repository()
    private val adapter = Adapter(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        val recyclerView = findViewById<RecyclerView>(R.id.chats_list_recycler)
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
        adapter.addNewChats(repository.getRandomChatList(0, 20))
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            private var isLoading = false

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                val totalItemIndex = layoutManager.itemCount - 1
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                if (!isLoading && lastVisibleItem == totalItemIndex) {
                    isLoading = true
                    val offset = adapter.itemCount
                    recyclerView.postDelayed({
                        val newChats = repository.getRandomChatList(offset, PAGE_SIZE_CHATS)
                        adapter.addNewChats(newChats)
                        isLoading = false
                    }, PAGE_LOADING_DURATION_MS)
                }
            }
        })
        initSwipeRefresh()
    }

    private fun initSwipeRefresh() {
        val swipeToRefresh = findViewById<SwipeRefreshLayout>(R.id.swipe_to_refresh)
        swipeToRefresh.setOnRefreshListener {
            swipeToRefresh.postDelayed({
                val chatsList = repository.updateChatsList()
                adapter.setChats(chatsList)
                swipeToRefresh.isRefreshing = false
            }, PAGE_LOADING_DURATION_MS)
        }
    }

    override fun onChatItemClick(chat: ChatItem) {
        val intent = Intent(this, ChatWindow::class.java)
//        intent.putExtra("message", chat.messages)
        startActivity(intent)
    }


}