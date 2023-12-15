package com.example.finalskillsync.Fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.finalskillsync.R
import com.example.finalskillsync.Room.Opp
import com.example.finalskillsync.Room.OppDatabase
import com.example.finalskillsync.Room.OppRepository
import com.example.finalskillsync.databinding.FragmentFavoriteBinding
import com.example.finalskillsync.databinding.FragmentHomeBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
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

        getAll()
     /*   addtoFavorite()*/

    }
    private fun getAll() {

        val title = arguments?.getString("titleForFavorite") ?: ""

        // Now you have the title, you can use it as needed
        Toast.makeText(requireContext(), "Received Title: $title", Toast.LENGTH_SHORT).show()
        val usersRef = database.child("Opportunity")
        val usersQuery = usersRef.orderByChild("title").equalTo(title)

        usersQuery.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (userSnapshot in dataSnapshot.children) {
                        oppId = (userSnapshot.child("oppId").value as? Long).toString()
                        benefit = (userSnapshot.child("benefit").value as? String).toString()
                        deadline = (userSnapshot.child("deadline").value as? Long).toString()
                        level = (userSnapshot.child("level").value as? String).toString()
                        linkOpp = (userSnapshot.child("link").value as? Long).toString()
                        titleOpp = (userSnapshot.child("title").value as? String).toString()
                        Toast.makeText(requireContext(), "${oppId}, $benefit", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    // Handle the case when the snapshot doesn't exist
                    Log.d("Firebase", "User data does not exist in the database.")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle database error
                Toast.makeText(
                    requireContext(),
                    "Database error: " + databaseError.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

    }



    private fun addtoFavorite() {

        val sharedPreferences =
            requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val useremail = sharedPreferences.getString("useremail", "")

        val oppDatabase = OppDatabase.getDatabase(requireContext())

        // Obtain the OppDao from the database
        val oppDao = oppDatabase.oppDao()
        val oppRepository = OppRepository(oppDao)

        // Launch a coroutine
        lifecycleScope.launch {
            // Use withContext to switch to a background thread (Dispatchers.IO)
            withContext(Dispatchers.IO) {
                oppRepository.addOpp(
                    Opp(
                        oppId = oppId.toLong(),
                        benefit = benefit,
                        deadline = deadline,
                        level = level,
                        link = linkOpp,
                        title = titleOpp,
                        userEmail = useremail
                    )
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}