package com.example.finalskillsync

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.finalskillsync.Fragment.HomeFragment
class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Display the HomeFragment by default
        showFragment(HomeFragment())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bottom_nav, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                // Show HomeFragment when "Home" is selected
                showFragment(HomeFragment())
                return true
            }
            // Uncomment the following sections for other menu items
//            R.id.favorite -> {
//                showFragment(FavoriteFragment())
//                return true
//            }
//            R.id.profile -> {
//                showFragment(ProfileFragment())
//                return true
//            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.homeFrame, fragment)
            // Remove the line below if you don't want to add the transaction to the back stack
            .addToBackStack(null)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
    }
}
