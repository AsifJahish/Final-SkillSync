package com.example.finalskillsync.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.finalskillsync.Firebase.Models.Notes
import com.example.finalskillsync.R
import com.example.finalskillsync.databinding.FragmentFavoriteBinding
import com.example.finalskillsync.databinding.FragmentNoteDetailBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class NoteDetailFragment : Fragment() {

    private var _binding: FragmentNoteDetailBinding? = null
    private val binding get() = _binding!!

    companion object {

        fun newInstance() =
            NoteDetailFragment().apply {

            }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNoteDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val title = arguments?.getString("titleForMemo") ?: ""

        val databaseRef = FirebaseDatabase.getInstance().reference.child("Memo")

        // Query the database based on the title
        val query = databaseRef.orderByChild("title").equalTo(title)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    // Get the first child (assuming there's only one match)
                    val noteSnapshot = snapshot.children.first()

                    // Convert the DataSnapshot to a Notes object
                    val note = noteSnapshot.getValue(Notes::class.java)

                    if (note != null) {
                        // Update the TextView with the retrieved data
                        binding.titleText.text = note.title
                        binding.detialText.text = note.detial // Corrected here

                    } else {
                        // Handle the case where no data is found
                        Toast.makeText(requireContext(), "Empty", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle the error
                // You can log the error or display a message to the user
                Toast.makeText(requireContext(), "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}