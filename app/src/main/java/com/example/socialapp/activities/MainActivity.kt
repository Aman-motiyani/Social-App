package com.example.socialapp.activities

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.socialapp.R
import com.example.socialapp.views.MainFragment
import com.example.socialapp.views.PostFragment
import com.example.socialapp.views.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    lateinit var bottomNav : BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNav = findViewById<BottomNavigationView>(R.id.btmNav)

        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    loadFragment(MainFragment())
                    true
                }
                R.id.addButton -> {
                    loadFragment(PostFragment())
                    true
                }
                R.id.profile -> {
                    loadFragment(ProfileFragment())
                    true
                }

                else -> true
            }
        }

        bottomNav.selectedItemId = R.id.home

        setSupportActionBar(toolbar)


    }

    private  fun loadFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout,fragment)
            addToBackStack(null)
            commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.logout,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.message) {
            val intent = Intent(this , ChatActivity::class.java)
            startActivity(intent)
        }

        if (item.itemId == R.id.newPost) {
            loadFragment(PostFragment())
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        loadFragment(MainFragment())
        super.onResume()
    }

}