package com.example.finalskillsync.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.finalskillsync.API.DoModel.DoItem
import com.example.finalskillsync.API.DoRequest.RetrofitInstanceDo
import com.example.finalskillsync.API.Model.Meme
import com.example.finalskillsync.API.Request.RetrofitInstance
import com.example.finalskillsync.Adatpers.DoListAdapter
import com.example.finalskillsync.Adatpers.RvAdapter

import com.example.finalskillsync.databinding.FragmentHomeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var rvAdapter: RvAdapter
    private lateinit var doAdapter: DoListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Your additional code here

        getMemes()
/*        getDo()*/
    }



    fun getDo(){

        GlobalScope.launch(Dispatchers.IO) {
            val response = try {
                RetrofitInstanceDo.api.getDo()

            } catch (e: IOException) {
                Toast.makeText(requireContext(), "app error ${e.message}", Toast.LENGTH_SHORT)
                    .show()
                return@launch
            } catch (e: HttpException) {
                Toast.makeText(requireContext(), "http error: ${e.message}", Toast.LENGTH_SHORT)
                    .show()
                return@launch
            }
            if (response.isSuccessful && response.body() != null) {
                withContext(Dispatchers.Main) {
                    val doList: List<DoItem> = response.body()!!
                    binding.apply {
                        /*progressBar.visibility = View.GONE*/
                        doAdapter = DoListAdapter(doList)
                        binding.doRecycleView.adapter = doAdapter
                        binding.doRecycleView.layoutManager =
                            StaggeredGridLayoutManager(1, RecyclerView.VERTICAL)
                    }
                }
            }
        }

    }


/*    fun getMemes(){
        GlobalScope.launch(Dispatchers.IO) {
            val response = try {
                RetrofitInstance.api.getMemes()

            } catch (e: IOException) {
                Toast.makeText(requireContext(), "app error ${e.message}", Toast.LENGTH_SHORT)
                    .show()
                return@launch
            } catch (e: HttpException) {
                Toast.makeText(requireContext(), "http error: ${e.message}", Toast.LENGTH_SHORT)
                    .show()
                return@launch
            }
            if (response.isSuccessful && response.body() != null) {
                withContext(Dispatchers.Main) {
                    val memesList: List<Meme> = response.body()!!.data.memes
                    binding.apply {
                       *//* progressBar.visibility = View.GONE*//*
                        rvAdapter = RvAdapter(memesList)
                        binding.rcycleView.adapter = rvAdapter
                        binding.rcycleView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
                    }
                }
            }
        }

    }*/

    fun getMemes() {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.api.getMemes()

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val memesList: List<Meme> = response.body()?.data?.memes ?: emptyList()
                        rvAdapter = RvAdapter(memesList)
                        binding.rcycleView.apply {
                            adapter = rvAdapter
                            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
                        }
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "HTTP error: ${response.code()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: IOException) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "App error: ${e.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            } catch (e: HttpException) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "HTTP error: ${e.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }


    companion object {
        fun newInstance() = HomeFragment().apply {
            // You can still perform any initialization here if needed
        }
    }
}