package com.example.finalskillsync.Adatpers


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalskillsync.API.DoModel.DoData
import com.example.finalskillsync.API.DoModel.DoItem

import com.example.finalskillsync.databinding.DoListBinding

class DoListAdapter(private val doList: List<DoItem>): RecyclerView.Adapter<DoListAdapter.ViewHolder>() {
    class ViewHolder(val binding: DoListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DoListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return doList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = doList[position]
        holder.binding.apply {
            act.text = currentItem.activity
            // Add other bindings if needed
        }
    }
}
