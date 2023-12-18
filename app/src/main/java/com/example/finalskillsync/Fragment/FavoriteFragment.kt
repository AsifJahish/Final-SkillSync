package com.example.finalskillsync.Fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalskillsync.Adatpers.FavoriteAdapter.FavoriteAdapter
import com.example.finalskillsync.ViewModel.FavoriteViewModel
import com.example.finalskillsync.ViewModel.FavoriteViewModelFactory
import com.example.finalskillsync.Model.Opp
import com.example.finalskillsync.Room.OppDatabase
import com.example.finalskillsync.Repository.OppRepository
import com.example.finalskillsync.databinding.FragmentFavoriteBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var database: DatabaseReference
    private lateinit var oppId: String
    private lateinit var benefit: String
    private lateinit var deadline: String
    private lateinit var level: String
    private lateinit var linkOpp: String
    private lateinit var titleOpp: String

    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var adapter: FavoriteAdapter
    private lateinit var oppRepository: OppRepository

    companion object {

        fun newInstance() =
            FavoriteFragment().apply {
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val title = arguments?.getString("titleForFavorite") ?: ""

        // Now you have the title, you can use it as needed

        getAll()

        val oppDao = OppDatabase.getDatabase(requireContext()).oppDao()

        // Initialize OppRepository
        oppRepository = OppRepository(oppDao)


        favoriteViewModel = ViewModelProvider(this, FavoriteViewModelFactory(oppRepository)).get(
            FavoriteViewModel::class.java)

        // Initialize RecyclerView Adapter
        adapter = FavoriteAdapter(requireContext(), mutableListOf())

        // Set up RecyclerView
        binding.favoriteRecycle.adapter = adapter
        binding.favoriteRecycle.layoutManager = LinearLayoutManager(requireContext())

        // Observe data from ViewModel
        favoriteViewModel.allOpps.observe(viewLifecycleOwner, { opps ->
            adapter.updateData(opps)
        })

    }
    private fun getAll() {
        database = FirebaseDatabase.getInstance().reference
        val title = arguments?.getString("titleForFavorite") ?: ""

  /*      Toast.makeText(requireContext(), "Received Title: $title", Toast.LENGTH_SHORT).show()*/
        val oppRef = database.child("Opportunity")
        val usersQuery = oppRef.orderByChild("title").equalTo(title)

        usersQuery.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (userSnapshot in dataSnapshot.children) {
                        oppId = (userSnapshot.child("oppId").value?.toString() ?: "").takeIf { it.isNotEmpty() }
                            ?: " "
                        benefit = (userSnapshot.child("benefit").value as? String) ?: " "
                        deadline = (userSnapshot.child("deadline").value as? String) ?: " "
                        level = (userSnapshot.child("level").value as? String) ?: " "
                        linkOpp = (userSnapshot.child("link").value as? String) ?: " "
                        titleOpp = (userSnapshot.child("title").value as? String) ?: " "

                        // Check if any of the essential values are not empty
                        if (oppId.isNotEmpty() && titleOpp.isNotEmpty()
                        ) {
                          /*  Toast.makeText(
                                requireContext(),
                                "${oppId}, $benefit",
                                Toast.LENGTH_SHORT
                            ).show()*/

                            // Call addtoFavorite() here, after ensuring that the data is available
                            addtoFavorite()
                        } else {
                            Log.e("FirebaseData", "One or more essential values are empty.")
                        }
                    }
                } else {
                    Log.d("Firebase", "No data found for the given title.")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(
                    requireContext(),
                    "Database error: " + databaseError.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }


    private fun addtoFavorite() {
        val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val useremail = sharedPreferences.getString("useremail", "")

        // Assuming oppId, benefit, deadline, level, linkOpp, titleOpp are already initialized
        if (oppId != null) {
            val oppDatabase = OppDatabase.getDatabase(requireContext())
            val oppDao = oppDatabase.oppDao()
            val oppRepository = OppRepository(oppDao)

            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    try {
                        oppRepository.addOp(
                            Opp(
                                oppId = oppId.toLongOrNull() ?: -1L,  // Provide a default value, adjust as needed
                                benefit = benefit ?: "",
                                deadline = deadline ?: "",
                                level = level ?: "",
                                link = linkOpp ?: "",
                                title = titleOpp ?: "",
                                userEmail = useremail ?: ""
                            )
                        )
                        Log.d("RoomInsertion", "Data inserted successfully into Room")
                    } catch (e: Exception) {
                        Log.e("RoomInsertion", "Error inserting data into Room", e)
                    }
                }
            }
        } else {
            Log.e("RoomInsertion", "oppId is not initialized")
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}