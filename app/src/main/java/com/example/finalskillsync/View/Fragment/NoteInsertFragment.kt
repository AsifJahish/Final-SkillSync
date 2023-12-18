package com.example.finalskillsync.View.Fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import com.example.finalskillsync.Model.Notes
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

        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        binding.addButton.setOnClickListener {

            sendMemo()

            binding.firstEditText.text.clear()
            binding.secondEditText.text.clear()

        }



        }


    private fun sendMemo() {
        val databaseRef = FirebaseDatabase.getInstance().reference.child("Memo")
        val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val useremail = sharedPreferences.getString("useremail", "")
        val chat = Notes()
        val noteId = chat.noteId
        val titleMemo = binding.firstEditText.text.toString()
        val detail = binding.secondEditText.text.toString()

        if (titleMemo.isEmpty() && detail.isEmpty()) {
            Toast.makeText(requireContext(), "empty", Toast.LENGTH_SHORT).show()
        } else {
            // Rest of your code, e.g., constructing and saving the chat message
            val comment = Notes(
                noteId = noteId,
                title = titleMemo,
                detial = detail,
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

                        // Dismiss the fragment after successfully saving the comment
                        dismiss()
                    }
                    .addOnFailureListener { exception ->
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

