package com.example.finalskillsync.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.example.finalskillsync.R
import com.example.finalskillsync.databinding.ActivityMainBinding
import com.example.finalskillsync.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

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


}