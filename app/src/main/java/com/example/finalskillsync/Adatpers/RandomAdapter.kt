package com.example.finalskillsync.Adatpers

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalskillsync.API.Model.TodoList
import com.example.finalskillsync.R


class RandomAdapter(private val context: Context) :
    RecyclerView.Adapter<RandomAdapter.RecipeViewHolder>() {

    private var recipeList: List<TodoList> = emptyList()

    fun setRecipes(recipes: List<TodoList>) {
        recipeList = recipes
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.randome_list, parent, false)
        return RecipeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipeList[position]

        holder.recipeName.text = recipe.activity

        // Add click listener for the favorite button if available in your layout
        holder.favoriteButton.setOnClickListener {
            // Handle favorite button click
            // You can implement your logic here, such as adding the recipe to favorites
        }
    }

    override fun getItemCount(): Int {
        return recipeList.size
    }

    class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val recipeName: TextView = itemView.findViewById(R.id.recipeName)
        val favoriteButton: View = itemView.findViewById(R.id.favoriteButton) // Replace with your actual favorite button ID
    }
}