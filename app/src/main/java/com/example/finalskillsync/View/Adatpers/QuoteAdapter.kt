package com.example.finalskillsync.View.Adatpers

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.finalskillsync.Model.Quotes
import com.example.finalskillsync.View.Adatpers.DiffUtil.QuoteDiffUtil
import com.example.finalskillsync.databinding.RvItemBinding


class QuoteAdapter(private val context: Context, private var quotesList: List<Quotes>) : RecyclerView.Adapter<QuoteAdapter.ViewHolder>() {

    class ViewHolder(val binding: RvItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    fun updateData(newList: List<Quotes>) {
        val diffResult = DiffUtil.calculateDiff(QuoteDiffUtil(quotesList, newList))
        quotesList = newList
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return quotesList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = quotesList[position]
        holder.binding.apply {
            textView.text = currentItem.text
            authortext.text= currentItem.author
        }
    }
}