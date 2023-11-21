package com.example.finalskillsync.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.example.finalskillsync.Firebase.Users
import com.example.finalskillsync.R
import com.example.finalskillsync.databinding.ActivityMainBinding
import com.example.finalskillsync.databinding.FragmentLoginBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var dbref: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout using View Binding
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.singUpButton.setOnClickListener {
            fragmentTransaction()
        }
        binding.loginButton.setOnClickListener {
            getUsers()

        }
        binding.forgotPasswordTextView.setOnClickListener {
            travelToForgot()

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Release the View Binding instance
        _binding = null
    }

    companion object {

        fun newInstance() =
            LoginFragment().apply {
            }
    }
    private fun fragmentTransaction(){
        val fragment = SignUpFragment() // Instantiate the destination fragment
        val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
        transaction.replace(R.id.frameContainer, fragment)
        transaction.addToBackStack(null) // Optional: add the transaction to the back stack
        transaction.commit()
    }
    private fun travelToHome(){
        val fragment = HomeFragment() // Instantiate the destination fragment
        val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
        transaction.replace(R.id.frameContainer, fragment)
        transaction.addToBackStack(null) // Optional: add the transaction to the back stack
        transaction.commit()

    }
    private fun travelToForgot(){
        val fragment= ForgotFragment()
        val transaction: FragmentTransaction= requireFragmentManager().beginTransaction()
        transaction.replace(R.id.frameContainer, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun getUsers(){


        dbref = FirebaseDatabase.getInstance().reference.child("Users")

        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditTextL.text.toString()

        dbref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (userSnapshot in dataSnapshot.children) {
                        val user = userSnapshot.getValue(Users::class.java)
                        if (user?.email == email && user.password == password) {
                            Toast.makeText(requireContext(), "successful", Toast.LENGTH_SHORT).show()
                           travelToHome()
                        }

                    }
                    Toast.makeText(requireContext(), "wrong email or Password", Toast.LENGTH_SHORT).show()
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle database error
                    Toast.makeText(requireContext(), "Database error: " + databaseError.message, Toast.LENGTH_SHORT).show()
                }
            })
        }


}