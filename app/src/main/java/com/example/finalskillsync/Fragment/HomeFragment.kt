package com.example.finalskillsync.Fragment

import android.os.Bundle
import android.util.Log
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
import com.example.finalskillsync.R
import com.google.firebase.database.FirebaseDatabase
import com.example.finalskillsync.databinding.FragmentHomeBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseException
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
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
    private lateinit var oppAdapter: OppAdapter
    private lateinit var oppRef: DatabaseReference

    companion object {
        fun newInstance() =
            HomeFragment.apply {
                // You can still perform any initialization here if needed
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        getQuotes()
        getOpp()

    }





    private fun getOpp() {
        oppRef = FirebaseDatabase.getInstance().getReference("Opportunity")
        oppAdapter = OppAdapter(requireContext(), mutableListOf())
        binding.oppRecycleView.adapter = oppAdapter

        // Set up the layout manager for vertical display
        binding.oppRecycleView.layoutManager = LinearLayoutManager(requireContext())


        oppRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val oppList = mutableListOf<Opportunity>()

                for (oppSnapshot in snapshot.children) {
                    try {
                        val opportunity = oppSnapshot.getValue(Opportunity::class.java)
                        opportunity?.let {
                            oppList.add(it)
                        }
                    } catch (e: DatabaseException) {
                        Log.e("HomeFragment", "Error converting Opportunity", e)
                    }
                }

                Log.d("HomeFragment", "Number of opportunities: ${oppList.size}")

                oppAdapter.updateData(oppList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("HomeFragment", "Database error: ${error.message}")
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


