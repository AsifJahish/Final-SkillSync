package com.example.finalskillsync

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Profile
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.finalskillsync.Fragment.FavoriteFragment
import com.example.finalskillsync.Fragment.HomeFragment
import com.example.finalskillsync.Fragment.NoteFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomeActivity : AppCompatActivity() {

    private val homeFragment = HomeFragment()
    private val favoriteFragment = FavoriteFragment()
    private val noteFragment = NoteFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Display the HomeFragment by default
        val useremail = intent.getStringExtra("email")
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("useremail", useremail)
        editor.apply()

        val nav= findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        nav.setOnItemSelectedListener{ item ->
            when (item.itemId) {
                R.id.home -> replaceFragment(HomeFragment())
                R.id.favorite -> replaceFragment(FavoriteFragment())
                R.id.profile -> replaceFragment(NoteFragment())

                else -> {
                    // Handle other cases
                }
            }
            true
        }
        replaceFragment(HomeFragment())


    }

    private fun replaceFragment(fragment: Fragment) {
        Log.d("MyApp", "Replacing fragment with: ${fragment.javaClass.simpleName}")
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.homeFrame, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

}