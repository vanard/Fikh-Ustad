package com.iffy.fikhustaz.util

import android.Manifest
import android.app.Activity
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.BaseMultiplePermissionsListener

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