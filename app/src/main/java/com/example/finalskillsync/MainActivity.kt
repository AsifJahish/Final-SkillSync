package com.example.finalskillsync

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.finalskillsync.Fragment.LoginFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Handler().postDelayed({
            // Replace the welcome page with your desired fragment after the delay
            replaceFragment(LoginFragment())
        }, 1000) // 5000 milliseconds = 5 seconds
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameContainer, fragment)
        transaction.addToBackStack(null) // Optional: add the transaction to the back stack
        transaction.commit()
    }
}