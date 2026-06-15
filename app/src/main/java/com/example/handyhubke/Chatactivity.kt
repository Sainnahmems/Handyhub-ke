package com.example.handyhubke

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.core.graphics.toColorInt
import com.example.handyhubke.data.model.Message
import com.example.handyhubke.databinding.ActivityChatactivityBinding
import com.example.handyhubke.databinding.ItemMessageBubbleBinding

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatactivityBinding
    private val messagesList = mutableListOf<Message>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        messagesList.add(Message("1", "worker", "Hello, I am on my route to your destination details coordinates.", System.currentTimeMillis(), isFromMe = false))

        val adapter = object : RecyclerView.Adapter<ChatViewHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
                val b = ItemMessageBubbleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ChatViewHolder(b)
            }

            override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
                val m = messagesList[position]
                holder.bind(m)
            }
            override fun getItemCount() = messagesList.size
        }

        binding.rvChatMessages.layoutManager = LinearLayoutManager(this)
        binding.rvChatMessages.adapter = adapter

        binding.btnSendMessage.setOnClickListener {
            val txt = binding.etMessageInput.text.toString()
            if (txt.isNotEmpty()) {
                messagesList.add(Message("unique", "me", txt, System.currentTimeMillis(), isFromMe = true))
                adapter.notifyItemInserted(messagesList.size - 1)
                binding.etMessageInput.text.clear()
                binding.rvChatMessages.scrollToPosition(messagesList.size - 1)
            }
        }
    }

    class ChatViewHolder(private val b: ItemMessageBubbleBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(m: Message) {
            b.tvMsgText.text = m.text
            if (m.isFromMe) {
                b.root.gravity = android.view.Gravity.END
                b.cardMessage.setCardBackgroundColor("#BBDEFB".toColorInt())
            } else {
                b.root.gravity = android.view.Gravity.START
                b.cardMessage.setCardBackgroundColor("#E7E0EC".toColorInt())
            }
        }
    }
}