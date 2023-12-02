package com.example.finalskillsync.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalskillsync.API.Recipes
import com.example.finalskillsync.API.Request.RetrofitClient
import com.example.finalskillsync.Adatpers.RandomAdapter

import com.example.finalskillsync.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.HttpException
import java.io.IOException


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var recipeAdapter: RandomAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout using View Binding
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        recipeAdapter = RandomAdapter(requireContext())
        binding.randomRcycleView.apply {
            adapter = recipeAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }


        lifecycleScope.launch {
            try {
                val response: Response<List<Recipes>> = RetrofitClient.api.callRandomRecipe()

                if (response.isSuccessful && response.body() != null) {
                    val recipeList = response.body()!!
                    recipeAdapter.setRecipes(recipeList)
                } else {
                    Toast.makeText(requireContext(), "Unsuccessful response", Toast.LENGTH_LONG).show()
                }
            } catch (e: IOException) {
                Toast.makeText(requireContext(), "App error ${e.message}", Toast.LENGTH_LONG).show()
            } catch (e: HttpException) {
                Toast.makeText(requireContext(), "HTTP error ${e.message}", Toast.LENGTH_LONG).show()
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Unexpected error ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
}

    companion object {

        fun newInstance() =
            HomeFragment().apply {
            }
    }


}