package com.example.finalskillsync.Adatpers

import androidx.recyclerview.widget.DiffUtil
import com.example.finalskillsync.Model.Opportunity

class OppDiffUtil(private val oldList: List<Opportunity>, private val newList: List<Opportunity>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldOpp = oldList[oldItemPosition]
        val newOpp = newList[newItemPosition]

        return oldOpp.title == newOpp.title
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldOpp = oldList[oldItemPosition]
        val newOpp = newList[newItemPosition]

        return oldOpp.title == newOpp.title
    }
}
