package com.example.finalskillsync.Adatpers.FavoriteAdapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.finalskillsync.Fragment.FavoriteDetialFragment
import com.example.finalskillsync.R
import com.example.finalskillsync.Room.Opp
import com.example.finalskillsync.databinding.FavoriteListBinding

class FavoriteAdapter(private val context: Context,
    private var oppRList: MutableList<Opp>
    ): RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

        inner class  ViewHolder(private val binding:FavoriteListBinding): RecyclerView.ViewHolder(binding.root) {
            fun bind(current: Opp, context: Context) {
                binding.apply {
                    titleOpp.text = current.title
                    root.setOnClickListener{
                        val fragment = FavoriteDetialFragment()
                        val fragmentManager = (context as AppCompatActivity).supportFragmentManager

                        // Pass the title to the ChatFragment using arguments
                        val bundle = Bundle()
                        bundle.putString("titleForFD", current.title)
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

    fun updateData(newList: List<Opp>) {
        val diffResult = DiffUtil.calculateDiff(FavoriteDiffUtil(oppRList, newList))
        oppRList.clear()
        oppRList.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding= FavoriteListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current= oppRList[position]
        holder.bind(current,context)
    }

    override fun getItemCount(): Int {
        return oppRList.size
    }
}