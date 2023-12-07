package com.example.finalskillsync.Adatpers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalskillsync.Firebase.Models.Opportunity
import com.example.finalskillsync.databinding.ScholarshipListBinding

class OppAdapter(private val oppList:List<Opportunity>):RecyclerView.Adapter<OppAdapter.ViewHolder>() {

    class ViewHolder(val binding: ScholarshipListBinding):RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OppAdapter.ViewHolder {
        return OppAdapter.ViewHolder(ScholarshipListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
      return oppList.size
    }

    override fun onBindViewHolder(holder: OppAdapter.ViewHolder, position: Int) {
        val  currentItem= oppList[position]
        holder.binding.apply {
            titleOpp.text=currentItem.title
        }
    }
}