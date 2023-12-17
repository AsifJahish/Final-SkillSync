package com.example.finalskillsync.Adatpers.FavoriteAdapter

import androidx.recyclerview.widget.DiffUtil
import com.example.finalskillsync.Room.Opp

class FavoriteDiffUtil(private val oldList: List<Opp>, private val newList: List<Opp>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].oppId == newList[newItemPosition].oppId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldOpp = oldList[oldItemPosition]
        val newOpp = newList[newItemPosition]

        return oldOpp.title == newOpp.title
    }
}
