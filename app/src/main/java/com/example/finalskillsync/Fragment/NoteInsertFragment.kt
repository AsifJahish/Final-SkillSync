package com.example.finalskillsync.Fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.finalskillsync.Firebase.Models.Chat
import com.example.finalskillsync.Firebase.Models.Notes
import com.example.finalskillsync.R
import com.example.finalskillsync.databinding.FragmentNoteBinding
import com.example.finalskillsync.databinding.FragmentNoteInsertBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.FirebaseDatabase

class NoteInsertFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentNoteInsertBinding? = null
    private val binding get() = _binding!!
    companion object {

        fun newInstance() =
            NoteInsertFragment().apply {

            }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNoteInsertBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addButton.setOnClickListener {

            sendMemo()
        }



        }


    private fun sendMemo() {

        val databaseRef = FirebaseDatabase.getInstance().reference.child("Memo")
        val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val useremail = sharedPreferences.getString("useremail", "")
        val chat = Notes()
        val noteId= chat.noteId
        val titleMemo= binding.firstEditText.text.toString()
        val detial= binding.secondEditText.text.toString()
        if(titleMemo.isEmpty() && detial.isEmpty()){
            Toast.makeText(requireContext(), "empty", Toast.LENGTH_SHORT).show()
        }else{ // Rest of your code, e.g., constructing and saving the chat message
            val comment = Notes(
                noteId = noteId,
                title = titleMemo,
                detial = detial,
                userEmail = useremail,

            )

            noteId?.let {
                databaseRef.child(it.toString()).setValue(comment)
                    .addOnSuccessListener {
                        Toast.makeText(
                            requireContext(),
                            "Comment saved successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                    }.addOnFailureListener { exception ->
                        Toast.makeText(
                            requireContext(),
                            "Failed to save comment: ${exception.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            }
        }
    }

    }

