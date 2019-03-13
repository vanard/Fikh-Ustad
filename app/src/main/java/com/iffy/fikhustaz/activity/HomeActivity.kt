package com.iffy.fikhustaz.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.iffy.fikhustaz.fragment.HomeFragment
import com.iffy.fikhustaz.fragment.materi.MateriFragment
import com.iffy.fikhustaz.fragment.PesanFragment
import com.iffy.fikhustaz.R
import com.iffy.fikhustaz.R.id.*
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                nav_home -> {
                    loadFragment(savedInstanceState,
                        HomeFragment()
                    )
                    supportActionBar?.elevation = 0f
                }
                nav_pesan -> {
                    loadFragment(savedInstanceState, PesanFragment())
                    supportActionBar?.elevation = 0f
                }

                nav_materi -> {
                    loadFragment(savedInstanceState,
                        MateriFragment()
                    )
                    supportActionBar?.elevation = 0f
                }
            }
            true
        }
        bottom_navigation.selectedItemId = nav_home
    }

    private fun loadFragment(savedInstanceState: Bundle?, fm: Fragment) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container, fm)
                .commit()
        }
    }
}
