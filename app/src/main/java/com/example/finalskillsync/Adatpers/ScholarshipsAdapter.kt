package com.example.finalskillsync.Adatpers

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.example.finalskillsync.Firebase.Models.Opportunity
import com.example.finalskillsync.R

class ScholarshipsAdapter(
    private val context: Context,
    private val scholarshipList: ArrayList<Opportunity>
) : RecyclerView.Adapter<ScholarshipsAdapter.ViewHolder>() {

    private var filteredList: ArrayList<Opportunity> = scholarshipList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.opp_list, parent, false)
        return ViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = filteredList[position]
        holder.title.text = current.title ?: "N/A"
    }



    override fun getItemCount(): Int {
        return filteredList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        val title: TextView = itemView.findViewById(R.id.titleOpp)

    }
    fun filterList(filteredList: ArrayList<Opportunity>) {
        this.filteredList = filteredList
        notifyDataSetChanged()
    }
}