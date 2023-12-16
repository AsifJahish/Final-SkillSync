package com.example.finalskillsync.Adatpers

import com.example.finalskillsync.R
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity

import androidx.recyclerview.widget.RecyclerView
import com.example.finalskillsync.Firebase.Models.Opportunity
import com.example.finalskillsync.Fragment.ChatFragment
import com.example.finalskillsync.Fragment.FavoriteFragment
import com.example.finalskillsync.Fragment.HomeFragment

import com.example.finalskillsync.Fragment.OppDetailFragment
import com.example.finalskillsync.HomeActivity
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
    interface OppAdapterListener {
        fun onFavoriteClicked(title: String)
    }

    inner class ViewHolder(private val binding: OppListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(current: Opportunity, context: Context) {

            binding.apply {
                titleOpp.text = current.title ?: "N/A"
                root.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putString("Title", current.title)
                    val detailFragment = OppDetailFragment()
                    detailFragment.arguments = bundle
                    toDetail()
                }
                chat.setOnClickListener{

                    val fragment = ChatFragment()
                    val fragmentManager = (context as AppCompatActivity).supportFragmentManager

                    // Pass the title to the ChatFragment using arguments
                    val bundle = Bundle()
                    bundle.putString("titleForChat", current.title)
                    fragment.arguments = bundle

                    // Start a fragment transaction to replace the current fragment with the ChatFragment
                    fragmentManager.beginTransaction()
                        .replace(R.id.homeFrame, fragment)
                        .addToBackStack(null)
                        .commit()

                }
                favorite.setOnClickListener{
                    val fragment = FavoriteFragment()
                    val fragmentManager = (context as AppCompatActivity).supportFragmentManager

                    // Pass the title to the ChatFragment using arguments
                    val bundle = Bundle()
                    bundle.putString("titleForFavorite", current.title)
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

    fun updateData(newList: List<Opportunity>) {
        oppList.clear()
        oppList.addAll(newList)
        notifyDataSetChanged()
    }


        private fun toDetail(){
            val fragment = OppDetailFragment()
            val fragmentManager = (context as AppCompatActivity).supportFragmentManager

            fragmentManager.beginTransaction()
                .replace(R.id.homeFrame, fragment)
                .addToBackStack(null)
                .commit()

        }
    fun filterList(filteredList: ArrayList<Opportunity>) {
        this.filteredList = filteredList
        notifyDataSetChanged()
    }



}
