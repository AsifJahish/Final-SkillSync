package com.example.finalskillsync.Adatpers

import com.example.finalskillsync.R
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import androidx.recyclerview.widget.RecyclerView
import com.example.finalskillsync.Firebase.Models.Opportunity

import com.example.finalskillsync.Fragment.OppDetailFragment
import com.example.finalskillsync.databinding.OppListBinding


class OppAdapter(
    private val context: Context,
    private var oppList: MutableList<Opportunity>,

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

    inner class ViewHolder(private val binding: OppListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(current: Opportunity, context: Context) {

            binding.apply {
                titleOpp.text = current.title ?: "N/A"
                imageOpp.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putString("Title", current.title)
                    val detailFragment = OppDetailFragment()
                    detailFragment.arguments = bundle
                    toDetail()
                }
            }
        }
    }

    fun updateData(newList: List<Opportunity>) {
        oppList.clear()
        oppList.addAll(newList)
        notifyDataSetChanged()
    }


        private fun toDetail(){
            val fragment = OppDetailFragment()


            // Get the fragmentManager
            val fragmentManager = (context as AppCompatActivity).supportFragmentManager

            // Replace the fragment_container with the new fragment
            fragmentManager.beginTransaction()
                .replace(R.id.homeFrame, fragment)
                .addToBackStack(null) // Add to back stack so the user can navigate back
                .commit()

        }



}
