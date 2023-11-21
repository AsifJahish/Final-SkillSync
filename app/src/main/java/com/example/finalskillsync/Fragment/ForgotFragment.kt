package com.example.finalskillsync.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.finalskillsync.R
import com.example.finalskillsync.databinding.FragmentForgotBinding
import com.example.finalskillsync.databinding.FragmentSignUpBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class ForgotFragment : Fragment() {
    private var _binding: FragmentForgotBinding? = null
    private val binding get() = _binding!!
    private lateinit var dbref: DatabaseReference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentForgotBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.resetButton.setOnClickListener {
            binding.confirmCode.visibility = View.VISIBLE
            binding.newPassword.visibility = View.VISIBLE
            binding.confirmNewPassword.visibility = View.VISIBLE

        }

    }
companion object {
    fun newInstance() =
        ForgotFragment().apply {
            // You can still perform any initialization here if needed
        }
}
}