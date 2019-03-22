package com.iffy.fikhustaz.views.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.iffy.fikhustaz.views.fragment.home.HomeFragment
import com.iffy.fikhustaz.views.fragment.materi.MateriFragment
import com.iffy.fikhustaz.views.fragment.pesan.PesanFragment
import com.iffy.fikhustaz.R
import com.iffy.fikhustaz.R.id.*
import com.iffy.fikhustaz.views.activity.login.LoginActivity
import com.iffy.fikhustaz.util.permissionCheck
import kotlinx.android.synthetic.main.activity_home.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        if (FirebaseAuth.getInstance().currentUser == null){
            finish()
            startActivity(intentFor<LoginActivity>().newTask().clearTask())
        }
        permissionCheck(this)

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
