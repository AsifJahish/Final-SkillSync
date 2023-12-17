package com.example.finalskillsync.Fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalskillsync.Adatpers.NoteAdapter.NoteAdapter
import com.example.finalskillsync.Firebase.Models.Notes
import com.example.finalskillsync.databinding.FragmentNoteBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseException
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class NoteFragment : Fragment() {
    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!
    private lateinit var noteAdapter: NoteAdapter
    private lateinit var noteRef: DatabaseReference
    companion object {

        fun newInstance() =
            NoteFragment().apply {

            }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addBtn.setOnClickListener {
            val fragment = NoteInsertFragment()
            fragment.show(requireActivity().supportFragmentManager, fragment.tag)
        }

        binding.noteRecycle.layoutManager = LinearLayoutManager(requireContext())

        // Initialize noteAdapter and set it to the RecyclerView
        noteAdapter = NoteAdapter(requireContext(), mutableListOf()) // Initialize with an empty list
        binding.noteRecycle.adapter = noteAdapter

        getOpp()
    }
    private fun getOpp() {
        val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val useremail = sharedPreferences.getString("useremail", "")

        // Set up the layout manager for vertical display
        binding.noteRecycle.layoutManager = LinearLayoutManager(requireContext())


        if (!useremail.isNullOrEmpty()) {
            noteRef = FirebaseDatabase.getInstance().getReference("Memo")

            noteRef.orderByChild("userEmail").equalTo(useremail)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val oppList = mutableListOf<Notes>()

                        for (oppSnapshot in snapshot.children) {
                            try {
                                val opportunity = oppSnapshot.getValue(Notes::class.java)
                                opportunity?.let {
                                    oppList.add(it)
                                }
                            } catch (e: DatabaseException) {
                                Log.e("HomeFragment", "Error converting Opportunity", e)
                            }
                        }

                        Log.d("HomeFragment", "Number of opportunities: ${oppList.size}")

                        noteAdapter.updateData(oppList)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.e("HomeFragment", "Database error: ${error.message}")
                    }
                })
        }
    }


}