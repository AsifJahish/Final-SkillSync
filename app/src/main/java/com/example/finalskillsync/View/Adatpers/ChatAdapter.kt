package com.example.finalskillsync.View.Adatpers

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.finalskillsync.Model.Chat
import com.example.finalskillsync.View.Adatpers.DiffUtil.ChatDiffUtil
import com.example.finalskillsync.databinding.ChatListBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class ChatAdapter (private val context: Context,
                   private var chatList: MutableList<Chat>
): RecyclerView.Adapter<ChatAdapter.ViewHolder>(){

    inner class  ViewHolder(private val binding: ChatListBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(current: Chat, context: Context) {
            binding.apply {
                senderName.text= current.userName.toString()
                chatMe.text= current.chat.toString()
                val timeInMillis = current.timestamp
                val calendar = Calendar.getInstance()
                calendar.timeInMillis = timeInMillis
                val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                val formattedTime = dateFormat.format(calendar.time)
                time.text = formattedTime

            }

        }

    }
    fun updateData(newList: List<Chat>) {
        val diffResult = DiffUtil.calculateDiff(ChatDiffUtil(chatList, newList))
        chatList.clear()
        chatList.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
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