package com.iffy.fikhustaz.views.activity

import android.view.ActionMode
import androidx.appcompat.app.AppCompatActivity
import com.iffy.fikhustaz.util.FirebaseUtil

abstract class BaseActivity: AppCompatActivity(){

    override fun onPause() {
        super.onPause()
        FirebaseUtil.updateStatusOnline("offline")
    }

    override fun onResume() {
        super.onResume()
        FirebaseUtil.updateStatusOnline("online")
    }
}