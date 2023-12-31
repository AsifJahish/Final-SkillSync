package com.example.finalskillsync.View.Adatpers

import com.example.finalskillsync.R
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.finalskillsync.Model.Opportunity
import com.example.finalskillsync.View.Adatpers.DiffUtil.OppDiffUtil
import com.example.finalskillsync.View.Fragment.ChatFragment
import com.example.finalskillsync.View.Fragment.FavoriteFragment
import com.example.finalskillsync.View.Fragment.OppDetailFragment
import com.example.finalskillsync.databinding.OppListBinding

class OppAdapter(
    private val context: Context,
    private var oppList: MutableList<Opportunity>,
) : RecyclerView.Adapter<OppAdapter.ViewHolder>() {

    private var filteredList: MutableList<Opportunity> = oppList

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
                titleOpp.setOnClickListener {
                    val fragment = OppDetailFragment()
                    val fragmentManager = (context as AppCompatActivity).supportFragmentManager

                    val bundle = Bundle()
                    bundle.putString("Title", current.title)
                    fragment.arguments = bundle

                    fragmentManager.beginTransaction()
                        .replace(R.id.homeFrame, fragment)
                        .addToBackStack(null)
                        .commit()

                }
                chat.setOnClickListener {
                    val fragment = ChatFragment()
                    val fragmentManager = (context as AppCompatActivity).supportFragmentManager

                    val bundle = Bundle()
                    bundle.putString("titleForChat", current.title)
                    fragment.arguments = bundle

                    fragmentManager.beginTransaction()
                        .replace(R.id.homeFrame, fragment)
                        .addToBackStack(null)
                        .commit()
                }
                favorite.setOnClickListener {
                    val fragment = FavoriteFragment()
                    val fragmentManager = (context as AppCompatActivity).supportFragmentManager

                    val bundle = Bundle()
                    bundle.putString("titleForFavorite", current.title)
                    fragment.arguments = bundle

                    fragmentManager.beginTransaction()
                        .replace(R.id.homeFrame, fragment)
                        .addToBackStack(null)
                        .commit()
                }
            }
        }
    }

    fun updateData(newList: List<Opportunity>) {
        // Calculate the diff result
        val diffResult = DiffUtil.calculateDiff(OppDiffUtil(oppList, newList))
        oppList.clear()
        oppList.addAll(newList)

        diffResult.dispatchUpdatesTo(this)

    }


    fun filterList(filteredList: List<Opportunity>) {
        oppList.clear()
        oppList.addAll(filteredList)
        notifyDataSetChanged()
    }

}


