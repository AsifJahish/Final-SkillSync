package com.example.finalskillsync.Adatpers

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalskillsync.Firebase.Models.Chat
import com.example.finalskillsync.Firebase.Models.Notes
import com.example.finalskillsync.databinding.NoteListBinding

class NoteAdapter(private val context: Context,
    private var noteList: MutableList<Notes>):
RecyclerView.Adapter<NoteAdapter.ViewHolder>(){


    inner class ViewHolder(private val binding: NoteListBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(current: Notes, context: Context){
            binding.apply{
                titleNote.text= current.title
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteAdapter.ViewHolder {

        val binding= NoteListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteAdapter.ViewHolder, position: Int) {
        val current= noteList[position]
        holder.bind(current, context)


    }

    override fun getItemCount(): Int {
       return noteList.size
    }
    fun updateData(newList: List<Notes>) {
        noteList.clear()
        noteList.addAll(newList)
        notifyDataSetChanged()
    }

}