package com.example.finalskillsync.Adatpers.NoteAdapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.finalskillsync.Model.Notes
import com.example.finalskillsync.Fragment.NoteDetailFragment
import com.example.finalskillsync.R
import com.example.finalskillsync.databinding.NoteListBinding

class NoteAdapter(private val context: Context,
    private var noteList: MutableList<Notes>):
RecyclerView.Adapter<NoteAdapter.ViewHolder>(){


    inner class ViewHolder(private val binding: NoteListBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(current: Notes, context: Context){
            binding.apply{
                titleNote.text= current.title
                root.setOnClickListener{
                    val fragment = NoteDetailFragment()
                    val fragmentManager = (context as AppCompatActivity).supportFragmentManager

                    // Pass the title to the ChatFragment using arguments
                    val bundle = Bundle()
                    bundle.putString("titleForMemo", current.title)
                    fragment.arguments = bundle

                    // Start a fragment transaction to replace the current fragment with the ChatFragment
                    fragmentManager.beginTransaction()
                        .replace(R.id.homeFrame, fragment)
                        .addToBackStack(null)
                        .commit()
                }
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding= NoteListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current= noteList[position]
        holder.bind(current, context)



    }

    override fun getItemCount(): Int {
       return noteList.size
    }
    fun updateData(newList: List<Notes>) {
        val diffResult = DiffUtil.calculateDiff(NoteDiffUtil(noteList, newList))
        noteList.clear()
        noteList.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

}