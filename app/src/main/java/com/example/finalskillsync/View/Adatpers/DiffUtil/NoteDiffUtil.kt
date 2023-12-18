package com.example.finalskillsync.View.Adatpers.DiffUtil

import androidx.recyclerview.widget.DiffUtil
import com.example.finalskillsync.Model.Notes

class NoteDiffUtil(private val oldList: List<Notes>, private val newList: List<Notes>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].noteId == newList[newItemPosition].noteId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldNote = oldList[oldItemPosition]
        val newNote = newList[newItemPosition]

        return oldNote.title == newNote.title
    }
}
