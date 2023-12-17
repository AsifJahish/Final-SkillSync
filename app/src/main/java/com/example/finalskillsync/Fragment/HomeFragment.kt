package com.example.finalskillsync.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalskillsync.API.Model.Quotes
import com.example.finalskillsync.API.Request.RetrofitInstance
import com.example.finalskillsync.Adatpers.OppAdapter
import com.example.finalskillsync.Adatpers.RVAdapter.QuoteAdapter
import com.example.finalskillsync.Firebase.Models.Opportunity
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
    private lateinit var opportunityList: ArrayList<Opportunity>

    private lateinit var quoteAdapter: QuoteAdapter
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


        binding.search.clearFocus()

        opportunityList = ArrayList()
        oppAdapter = OppAdapter(requireContext(), opportunityList)
        binding.oppRecycleView.adapter = oppAdapter


        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                    // If the text is empty, refresh the list to show the original, unfiltered list
                    refreshList()
                } else {
                    // If there is a query, perform the search as usual
                    filterScholarships(newText)
                }
                return true
            }
        })
        getQuotes()
        getOpp()

    }    private fun filterScholarships(query: String) {
        val filteredList = ArrayList<Opportunity>()

        for (scholarship in opportunityList) {
            if (oppContainsQuery(scholarship, query)) {
                filteredList.add(scholarship)
            }
        }

        oppAdapter.filterList(filteredList)
    }

    private fun oppContainsQuery(opportunity: Opportunity, query: String): Boolean {
        val lowercaseQuery = query.toLowerCase()

        // Check if any attribute of the Scholarships object contains the lowercase search query
        return opportunity.title?.toLowerCase()?.contains(lowercaseQuery) == true ||
                opportunity.level?.toLowerCase()?.contains(lowercaseQuery) == true ||
                opportunity.link?.toLowerCase()?.contains(lowercaseQuery) == true ||
                opportunity.benefit?.toLowerCase()?.contains(lowercaseQuery) == true ||
                opportunity.deadline?.toLowerCase()?.contains(lowercaseQuery) == true
    }


    private fun refreshList() {
        // Call this method when you want to show the original, unfiltered list
        oppAdapter.filterList(opportunityList)
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
                        quoteAdapter = QuoteAdapter(requireContext(),quotes)
                        binding.rcycleView.apply {
                            adapter = quoteAdapter
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


