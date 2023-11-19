package com.example.finalskillsync.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.example.finalskillsync.Firebase.Users
import com.example.finalskillsync.R
import com.example.finalskillsync.databinding.FragmentLoginBinding
import com.example.finalskillsync.databinding.FragmentSignUpBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpFragment : Fragment() {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private lateinit var dbref: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dbref = FirebaseDatabase.getInstance().reference.child("Users")

        binding.signUpButton.setOnClickListener {
          saveUser()


            // Replace the current fragment with the new loginFragment
            val loginFragment = LoginFragment()
            val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
            fragmentManager.beginTransaction()
                .replace(R.id.frameContainer, loginFragment)
                .addToBackStack(null) // This allows the user to go back to the previous fragment when pressing the back button
                .commit()
        }
    }
    companion object {
        fun newInstance() =
            SignUpFragment().apply {
                // You can still perform any initialization here if needed
            }
    }

    private fun saveUser() {
        val name = binding.nameEditText.text.toString()
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        val phoneNumber = binding.phoneNumberEditText.text.toString().toInt()

        if (name.isEmpty() && email.isEmpty() && password.isEmpty()) {
            Toast.makeText(context, "Name is empty", Toast.LENGTH_SHORT).show()
            return
        }



        val newUser = Users()
        val id = newUser.userId
        val users = Users(id, name, email, phoneNumber,password, )
        dbref.child(id.toString()).setValue(users)
            .addOnCompleteListener {
                Toast.makeText(context, "User registration completed", Toast.LENGTH_SHORT).show()

                // Replace the current SignUpFragment with the loginFragment
            }
            .addOnFailureListener { err ->
                Toast.makeText(context, "Error: ${err.message}", Toast.LENGTH_SHORT).show()
            }
    }

}