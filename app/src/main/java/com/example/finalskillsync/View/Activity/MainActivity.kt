package com.example.finalskillsync.View.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.finalskillsync.R
import com.example.finalskillsync.View.Fragment.LoginFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Handler().postDelayed({
            // Replace the welcome page with your desired fragment after the delay
            replaceFragment(LoginFragment())
        }, 1000) // 1000 milliseconds = 1 second
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()

        // Hide the views in the frameContainer
        val frameContainer = findViewById<FrameLayout>(R.id.frameContainer)
        for (i in 0 until frameContainer.childCount) {
            val child = frameContainer.getChildAt(i)
            child.visibility = View.GONE
        }

        // Replace the frameContainer with the new fragment
        transaction.replace(R.id.frameContainer, fragment)
        transaction.addToBackStack(null) // Optional: add the transaction to the back stack
        transaction.commit()
    }

}