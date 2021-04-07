package com.iffy.fikhustaz.views.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.iffy.fikhustaz.views.fragment.home.HomeFragment
import com.iffy.fikhustaz.views.fragment.materi.MateriFragment
import com.iffy.fikhustaz.views.fragment.pesan.PesanFragment
import com.iffy.fikhustaz.R
import com.iffy.fikhustaz.R.id.*
import com.iffy.fikhustaz.data.AppConst
import com.iffy.fikhustaz.util.FirebaseUtil
import com.iffy.fikhustaz.views.activity.login.LoginActivity
import com.iffy.fikhustaz.util.permissionCheck
import com.iffy.fikhustaz.views.fragment.hadits.HadistFragment
import com.iffy.fikhustaz.views.fragment.quran.QuranFragment
import kotlinx.android.synthetic.main.activity_home.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask

class HomeActivity : AppCompatActivity() {

    private val fragHome = HomeFragment()
    private val fragPesan = PesanFragment()
    private val fragMateri = MateriFragment()
    private val fgQuran = QuranFragment()
    private val fgHadist = HadistFragment()

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
                    showFragmentHome()
                    supportActionBar?.elevation = 0f
                }
                nav_pesan -> {
                    showFragmentPesan()
                    supportActionBar?.elevation = 0f
                }
                nav_materi -> {
                    showFragmentMateri()
                    supportActionBar?.elevation = 0f
                }
                nav_kitab -> {
                    showFragmentHadist()
                    supportActionBar?.elevation = 0f
                }
                nav_quran -> {
                    showFgQuran()
                    supportActionBar?.elevation = 0f
                }
            }
            true
        }

        if (intent.getStringExtra("frg") != null){
            val frg = intent.getStringExtra("frg")

            when(frg){
                AppConst.MATERI_ACTIVITY -> {
                    bottom_navigation.selectedItemId = nav_materi
                }
                AppConst.CHAT_ACTIVITY -> {
                    bottom_navigation.selectedItemId = nav_pesan
                }
                AppConst.EDIT_PROFILE_ACTIVITY -> bottom_navigation.selectedItemId = nav_home
                AppConst.QURAN_ACTIVITY -> bottom_navigation.selectedItemId = nav_quran
                AppConst.HADIST_ACTIVITY -> bottom_navigation.selectedItemId = nav_kitab

            }
        }else{
            bottom_navigation.selectedItemId = nav_home
        }
    }

    private fun showFragmentHome() {
        val ft = supportFragmentManager.beginTransaction()
        if (fragPesan.isAdded) {
            ft.hide(fragPesan)
        }
        if (fragMateri.isAdded) {
            ft.hide(fragMateri)
        }
        if (fgQuran.isAdded) {
            ft.hide(fgQuran)
        }
        if (fgHadist.isAdded) {
            ft.hide(fgHadist)
        }

        if (fragHome.isAdded)
            ft.show(fragHome)
        else
            ft.add(R.id.main_container,fragHome)

        ft.commit()
        supportActionBar?.title = "Home"
    }

    private fun showFragmentPesan() {
        val ft = supportFragmentManager.beginTransaction()
        if (fragHome.isAdded)
            ft.hide(fragHome)

        if (fragMateri.isAdded)
            ft.hide(fragMateri)

        if (fgQuran.isAdded) {
            ft.hide(fgQuran)
        }
        if (fgHadist.isAdded) {
            ft.hide(fgHadist)
        }

        if (fragPesan.isAdded)
            ft.show(fragPesan)
        else
            ft.add(R.id.main_container,fragPesan)

        ft.commit()
        supportActionBar?.title = "Pesan"
    }

    private fun showFragmentMateri() {
        val ft = supportFragmentManager.beginTransaction()
        if (fragPesan.isAdded)
            ft.hide(fragPesan)

        if (fragHome.isAdded)
            ft.hide(fragHome)

        if (fgQuran.isAdded) {
            ft.hide(fgQuran)
        }
        if (fgHadist.isAdded) {
            ft.hide(fgHadist)
        }

        if (fragMateri.isAdded)
            ft.show(fragMateri)
        else
            ft.add(R.id.main_container,fragMateri)

        ft.commit()
        supportActionBar?.title = "Materi"
    }

    private fun showFragmentHadist() {
        val ft = supportFragmentManager.beginTransaction()
        if (fragPesan.isAdded)
            ft.hide(fragPesan)

        if (fragHome.isAdded)
            ft.hide(fragHome)

        if (fgQuran.isAdded) {
            ft.hide(fgQuran)
        }
        if (fragMateri.isAdded) {
            ft.hide(fragMateri)
        }

        if (fgHadist.isAdded)
            ft.show(fgHadist)
        else
            ft.add(R.id.main_container,fgHadist)

        ft.commit()
        supportActionBar?.title = "Hadist"
    }

    private fun showFgQuran() {
        val ft = supportFragmentManager.beginTransaction()
        if (fragPesan.isAdded)
            ft.hide(fragPesan)

        if (fragHome.isAdded)
            ft.hide(fragHome)

        if (fragMateri.isAdded) {
            ft.hide(fragMateri)
        }
        if (fgHadist.isAdded) {
            ft.hide(fgHadist)
        }

        if (fgQuran.isAdded)
            ft.show(fgQuran)
        else
            ft.add(R.id.main_container,fgQuran)

        ft.commit()
        supportActionBar?.title = "Al - Qur'an"
    }

    override fun onPause() {
        super.onPause()
        FirebaseUtil.updateStatusOnline("offline")
    }

    override fun onResume() {
        super.onResume()
        FirebaseUtil.updateStatusOnline("online")
    }
}
