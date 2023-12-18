package com.example.finalskillsync.Adatpers.ChatAdapter

import androidx.recyclerview.widget.DiffUtil
import com.example.finalskillsync.Model.Chat

class ChatDiffUtil(private val oldList: List<Chat>, private val newList: List<Chat>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].chatId == newList[newItemPosition].chatId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldChat = oldList[oldItemPosition]
        val newChat = newList[newItemPosition]

        return oldChat.userName == newChat.userName && oldChat.chat == newChat.chat
    }
}
