package com.example.finalskillsync.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.example.finalskillsync.Firebase.Users
import com.example.finalskillsync.R
import com.example.finalskillsync.databinding.FragmentLoginBinding
import com.example.finalskillsync.databinding.FragmentSignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpFragment : Fragment() {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
private lateinit var dbref: DatabaseReference
private lateinit var auth: FirebaseAuth


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
        auth = FirebaseAuth.getInstance()

        logoPicture()

        binding.signUpButton.setOnClickListener {
            saveUser()
            loginTranscation()
            firebaseauthSave()
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
            Toast.makeText(context, "empty", Toast.LENGTH_SHORT).show()
            return
        }
        val newUser = Users()
        val id = newUser.userId
        val users = Users(id, name, email, phoneNumber,password, )
        dbref.child(id.toString()).setValue(users)
            .addOnCompleteListener {
                Toast.makeText(context, "User registration completed", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { err ->
                Toast.makeText(context, "Error: ${err.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun firebaseauthSave(){
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        if ( email.isEmpty() || password.isEmpty()) {
            Toast.makeText(context, "All fields are required", Toast.LENGTH_SHORT).show()
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(requireContext(), "approved", Toast.LENGTH_SHORT).show()
                } else {
                    // If sign up fails, display a message to the user.
                    Toast.makeText(
                        context, "Authentication failed. ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }


    private fun loginTransaction() {
        val loginFragment = LoginFragment()
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        fragmentManager.beginTransaction()
            .replace(R.id.frameContainer, loginFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun loginTranscation(){

        val loginFragment = LoginFragment()
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        fragmentManager.beginTransaction()
            .replace(R.id.frameContainer, loginFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun logoPicture(){
        context?.let {

            val url= "https://syncskills.net/wp-content/uploads/2022/03/syncskills-logo-twitter-card.png"

            val imagePath= binding.logoImageView

            Glide.with(this)
                .load(url)
                .into(imagePath)
        };
    }

}