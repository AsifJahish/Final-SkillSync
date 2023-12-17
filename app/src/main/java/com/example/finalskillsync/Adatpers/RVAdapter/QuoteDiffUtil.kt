package com.example.finalskillsync.Adatpers.RVAdapter

import androidx.recyclerview.widget.DiffUtil
import com.example.finalskillsync.API.Model.Quotes


class QuoteDiffUtil(private val oldList: List<Quotes>, private val newList: List<Quotes>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        // Replace "uniqueProperty" with the actual unique property in your Quotes class
        return oldList[oldItemPosition].uniqueProperty == newList[newItemPosition].uniqueProperty
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldQuote = oldList[oldItemPosition]
        val newQuote = newList[newItemPosition]

        return oldQuote.text == newQuote.text && oldQuote.author == newQuote.author
    }
}

