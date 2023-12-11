package com.example.finalskillsync.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.example.finalskillsync.HomeActivity
import com.example.finalskillsync.R
import com.example.finalskillsync.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

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
                    val bundle = Bundle()
                    bundle.putString("Email", email)
                    val chatFragment = ChatFragment()
                    chatFragment.arguments = bundle
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
            val url= "https://syncskills.net/wp-content/uploads/2022/03/syncskills-logo-twitter-card.png"
            val imagePath= binding.logoImageView

            Glide.with(this)
                .load(url)
                .into(imagePath)
        };
    }

}

/*
private fun activity2(){
    val intent = Intent(activity, MainActivity2::class.java)
    startActivity(intent)

}*/
