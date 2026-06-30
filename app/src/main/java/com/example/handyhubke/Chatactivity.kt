package com.example.handyhubke

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.handyhubke.data.model.Message
import com.example.handyhubke.databinding.ActivityChatactivityBinding
import com.example.handyhubke.databinding.ItemMessageBubbleBinding
import java.util.UUID

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatactivityBinding
    private val messagesList = mutableListOf<Message>()
    private lateinit var chatAdapter: MessagesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        // Initial message
        addMessage(
            Message(
                id = UUID.randomUUID().toString(),
                senderId = "worker",
                text = "Hello, I am on my route to your destination details coordinates.",
                timestamp = System.currentTimeMillis(),
                isFromMe = false
            )
        )

        binding.btnSendMessage.setOnClickListener {
            val text = binding.etMessageInput.text?.toString()?.trim() ?: ""
            if (text.isNotEmpty()) {
                val newMessage = Message(
                    id = UUID.randomUUID().toString(),
                    senderId = "me",
                    text = text,
                    timestamp = System.currentTimeMillis(),
                    isFromMe = true
                )
                addMessage(newMessage)
                binding.etMessageInput.text?.clear()
            }
        }
    }

    private fun setupRecyclerView() {
        chatAdapter = MessagesAdapter(messagesList)
        binding.rvChatMessages.apply {
            layoutManager = LinearLayoutManager(this@ChatActivity)
            adapter = chatAdapter
        }
    }

    private fun addMessage(message: Message) {
        messagesList.add(message)
        chatAdapter.notifyItemInserted(messagesList.size - 1)
        binding.rvChatMessages.scrollToPosition(messagesList.size - 1)
    }

    class MessagesAdapter(private val messages: List<Message>) :
        RecyclerView.Adapter<MessagesAdapter.ChatViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
            val binding = ItemMessageBubbleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return ChatViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
            holder.bind(messages[position])
        }

        override fun getItemCount() = messages.size

        class ChatViewHolder(private val binding: ItemMessageBubbleBinding) :
            RecyclerView.ViewHolder(binding.root) {

            fun bind(message: Message) {
                binding.tvMsgText.text = message.text
                val context = binding.root.context

                if (message.isFromMe) {
                    binding.root.gravity = Gravity.END
                    binding.cardMessage.setCardBackgroundColor(
                        ContextCompat.getColor(context, R.color.chat_bubble_me)
                    )
                } else {
                    binding.root.gravity = Gravity.START
                    binding.cardMessage.setCardBackgroundColor(
                        ContextCompat.getColor(context, R.color.chat_bubble_other)
                    )
                }
            }
        }
    }
}
