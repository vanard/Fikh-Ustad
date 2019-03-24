package com.iffy.fikhustaz.util

import android.Manifest
import android.app.Activity
import android.os.Bundle
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.BaseMultiplePermissionsListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.iffy.fikhustaz.R


fun permissionCheck(act: Activity){
    Dexter.withActivity(act)
        .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        .withListener(object: BaseMultiplePermissionsListener(){
            override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                super.onPermissionsChecked(report)
            }

            override fun onPermissionRationaleShouldBeShown(
                permissions: MutableList<PermissionRequest>?,
                token: PermissionToken?
            ) {
                super.onPermissionRationaleShouldBeShown(permissions, token)
            }
        }).check()
}

fun performNoBackStackTransaction(fragmentManager: FragmentManager, tag: String, fragment: Fragment ,savedInstanceState: Bundle?) {
    val newBackStackLength = fragmentManager.backStackEntryCount + 1

    if (savedInstanceState == null) {
        fragmentManager.beginTransaction()
            .replace(R.id.main_container, fragment, tag)
            .addToBackStack(tag)
            .commit()
    }

    fragmentManager.addOnBackStackChangedListener(object : FragmentManager.OnBackStackChangedListener {
        override fun onBackStackChanged() {
            val nowCount = fragmentManager.backStackEntryCount
            if (newBackStackLength != nowCount) {
                // we don't really care if going back or forward. we already performed the logic here.
                fragmentManager.removeOnBackStackChangedListener(this)

                if (newBackStackLength > nowCount) { // user pressed back
                    fragmentManager.popBackStackImmediate()
                }
            }
        }
    })
}