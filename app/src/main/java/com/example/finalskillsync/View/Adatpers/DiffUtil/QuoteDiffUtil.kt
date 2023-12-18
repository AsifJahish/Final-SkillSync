package com.example.finalskillsync.View.Adatpers.DiffUtil

import androidx.recyclerview.widget.DiffUtil
import com.example.finalskillsync.Model.Quotes

class QuoteDiffUtil(private val oldList: List<Quotes>, private val newList: List<Quotes>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldQuote = oldList[oldItemPosition]
        val newQuote = newList[newItemPosition]

        return oldQuote.text == newQuote.text && oldQuote.author == newQuote.author
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldQuote = oldList[oldItemPosition]
        val newQuote = newList[newItemPosition]

        return oldQuote.text == newQuote.text && oldQuote.author == newQuote.author
    }
}
