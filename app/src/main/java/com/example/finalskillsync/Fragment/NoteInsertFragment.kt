package com.example.finalskillsync.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.finalskillsync.R
import com.example.finalskillsync.databinding.FragmentNoteBinding
import com.example.finalskillsync.databinding.FragmentNoteInsertBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

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

        }

    }

