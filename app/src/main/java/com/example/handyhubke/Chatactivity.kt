package com.example.handyhubke

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.handyhubke.data.model.Message
import com.example.handyhubke.databinding.ActivityChatactivityBinding
import com.example.handyhubke.databinding.ItemMessageBubbleBinding

class Chatactivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatactivityBinding
    private val messagesList = mutableListOf<Message>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        messagesList.add(Message("1", "worker", "Hello, I am on my route to your destination details coordinates.", System.currentTimeMillis(), false))

        val adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                val b = ItemMessageBubbleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return object : RecyclerView.ViewHolder(b.root) {}
            }

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                val bItem = ItemMessageBubbleBinding.bind(holder.itemView)
                val m = messagesList[position]
                bItem.tvMsgText.text = m.text
                // Structural alignments dynamically generated layout properties rules logic definition
                if (m.isFromMe) {
                    bItem.root.gravity = android.view.Gravity.END
                    bItem.cardMessage.setCardBackgroundColor(android.graphics.Color.parseColor("#BBDEFB"))
                } else {
                    bItem.root.gravity = android.view.Gravity.START
                    bItem.cardMessage.setCardBackgroundColor(android.graphics.Color.parseColor("#E7E0EC"))
                }
            }
            override fun getItemCount() = messagesList.size
        }

        binding.rvChatMessages.layoutManager = LinearLayoutManager(this)
        binding.rvChatMessages.adapter = adapter

        binding.btnSendMessage.setOnClickListener {
            val txt = binding.etMessageInput.text.toString()
            if (txt.isNotEmpty()) {
                messagesList.add(Message("unique", "me", txt, System.currentTimeMillis(), true))
                adapter.notifyItemInserted(messagesList.size - 1)
                binding.etMessageInput.text.clear()
                binding.rvChatMessages.scrollToPosition(messagesList.size - 1)
            }
        }
    }
}