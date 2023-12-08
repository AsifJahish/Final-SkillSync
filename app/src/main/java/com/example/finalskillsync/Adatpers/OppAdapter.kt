package com.example.finalskillsync.Adatpers

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.example.finalskillsync.Firebase.Models.Opportunity
import com.example.finalskillsync.databinding.OppListBinding
class OppAdapter(
    private val context: Context,
    private var oppList: MutableList<Opportunity>
) : RecyclerView.Adapter<OppAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = OppListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = oppList[position]
        holder.bind(current, context)
    }

    override fun getItemCount(): Int {
        return oppList.size
    }

    class ViewHolder(private val binding: OppListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(current: Opportunity, context: Context) {
            binding.apply {
                titleOpp.text = current.title ?: "N/A"

            }
        }
    }

    fun updateData(newList: List<Opportunity>) {
        oppList.clear()
        oppList.addAll(newList)
        notifyDataSetChanged()
    }
}
