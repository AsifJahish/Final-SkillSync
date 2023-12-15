package com.example.finalskillsync.Adatpers

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalskillsync.Firebase.Models.Chat
import com.example.finalskillsync.Room.Opp
import com.example.finalskillsync.databinding.FavoriteListBinding
import com.example.finalskillsync.databinding.FragmentFavoriteBinding

class FavoriteAdapter(private val context: Context,
    private var oppRList: MutableList<Opp>
    ): RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

        inner class  ViewHolder(private val binding:FavoriteListBinding): RecyclerView.ViewHolder(binding.root){
            fun bind(current: Opp, context: Context){
                binding.apply {
                    titleOpp.text= current.title
                }
            }
        }

    fun updateData(newList: List<Opp>) {
        oppRList.clear()
        oppRList.addAll(newList)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteAdapter.ViewHolder {
        val binding= FavoriteListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteAdapter.ViewHolder, position: Int) {
        val current= oppRList[position]
        holder.bind(current,context)
    }

    override fun getItemCount(): Int {
        return oppRList.size
    }
}