package com.example.finalskillsync.View.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.example.finalskillsync.View.Activity.HomeActivity
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


        binding.signUP.setOnClickListener {
            fragmentTransaction()
        }
        binding.buttonLogin.setOnClickListener {
            getUsers()

        }
        binding.forgotPass.setOnClickListener {
            // Inside your activity or fragment where you want to show the bottom sheet
            val fragment = ForgotFragment.newInstance()
            fragment.show(requireFragmentManager(), fragment.tag)
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





    private fun getUsers() {
        val email = binding.email.text.toString()
        val password = binding.password.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(requireContext(), "Email and password are required", Toast.LENGTH_SHORT).show()
            return
        }

        // Authenticate the user using Firebase Authentication
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    val intent = Intent(requireContext(), HomeActivity::class.java)
                    intent.putExtra("email", email)
                    Toast.makeText(requireContext(), "susccesfull login", Toast.LENGTH_SHORT).show()

                    startActivity(intent)
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

}
