package com.example.finalskillsync.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalskillsync.API.Model.Quotes
import com.example.finalskillsync.API.Request.RetrofitInstance
import com.example.finalskillsync.Adatpers.OppAdapter
import com.example.finalskillsync.Adatpers.RvAdapter
import com.example.finalskillsync.Firebase.Models.Opportunity
import com.google.firebase.database.FirebaseDatabase
import com.example.finalskillsync.databinding.FragmentHomeBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.util.ArrayList


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var rvAdapter: RvAdapter
    private lateinit var dbref: DatabaseReference
    private val oppList: MutableList<Opportunity> = mutableListOf()
    private val oppAdapter = OppAdapter(oppList)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = binding.doRecycleView
        recyclerView.adapter = OppAdapter(oppList)
        getQuotes()
        getOpp()

    }


    private fun getOpp(){

        dbref = FirebaseDatabase.getInstance().getReference("Opportunity")


        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                oppList.clear()
                if (snapshot.exists()) {
                    for (booksnap in snapshot.children) {
                        val bookData = booksnap.getValue(Opportunity::class.java)
                        bookData?.let {
                            oppList.add(it)
                        }
                    }
                    oppAdapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle the cancellation if needed
            }
        })

    }





    fun getQuotes() {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.api.getMemes()

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val quotes: List<Quotes> = response.body()?: emptyList()
                        rvAdapter = RvAdapter(quotes)
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