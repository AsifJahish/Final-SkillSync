package com.example.finalskillsync.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.example.finalskillsync.Firebase.Users
import com.example.finalskillsync.HomeActivity
import com.example.finalskillsync.R
import com.example.finalskillsync.databinding.ActivityMainBinding
import com.example.finalskillsync.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var dbref: DatabaseReference

//    private lateinit var auth: FirebaseAuth

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

        logoPicture()


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
        val intent = Intent(activity, HomeActivity::class.java)
        startActivity(intent)

    }
    private fun travelToForgot(){
        val fragment= ForgotFragment()
        val transaction: FragmentTransaction= requireFragmentManager().beginTransaction()
        transaction.replace(R.id.frameContainer, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


    private fun getUsers() {
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditTextL.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(requireContext(), "Email and password are required", Toast.LENGTH_SHORT).show()
            return
        }

        // Authenticate the user using Firebase Authentication
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // User login is successful
                    Toast.makeText(requireContext(), "Login successful", Toast.LENGTH_SHORT).show()
                    travelToHome()
                } else {
                    // If sign-in fails, display a message to the user.
                    Toast.makeText(requireContext(), "Authentication failed. ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                // Handle exceptions that may occur during the authentication process
                Toast.makeText(requireContext(), "Authentication failed. ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun logoPicture(){
        context?.let {

            val url= "data:image/png;base64,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"

            val imagePath= binding.logoImageView

            Glide.with(this)
                .load(url)
                .into(imagePath)
        };
    }

}