package com.example.finalskillsync.Adatpers

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalskillsync.Firebase.Models.Chat
import com.example.finalskillsync.databinding.ChatListBinding

class ChatAdapter (private val context: Context,
    private var chatList: MutableList<Chat>
): RecyclerView.Adapter<ChatAdapter.ViewHolder>(){

        inner class  ViewHolder(private val binding:ChatListBinding ): RecyclerView.ViewHolder(binding.root){
            fun bind(current: Chat, context: Context) {
                binding.apply {
                    senderName.text= current.userId.toString()
                }

            }

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ChatListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val current= chatList[position]
        holder.bind(current, context)
    }
}