package com.iffy.fikhustaz.views.activity.editprof.upload

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.iffy.fikhustaz.R
import com.iffy.fikhustaz.views.activity.editprof.EditProfileActivity
import kotlinx.android.synthetic.main.dialog_upload.view.*
import org.jetbrains.anko.selector
import org.jetbrains.anko.toast
import java.io.ByteArrayOutputStream
import java.io.IOException



class DialogUploadFragment: DialogFragment() {

    private var selectedImageBytes: ByteArray? = null
    lateinit var v : View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.dialog_upload, container, false)

        val fighter = listOf("Camera", "Gallery")

        v.btn_pick_dialog.setOnClickListener {
            context!!.selector("Choose your fighter?", fighter) { dialogInterface, i ->
                when(fighter[i]){
                    "Camera" -> openCamera()
                    "Gallery" -> openGallery()
                }
            }
        }

        v.btn_upload_dialog.setOnClickListener {
            context!!.toast("ptk")
        }

        return v
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
        startActivityForResult(
            Intent.createChooser(intent, "Select Picture"),
            EditProfileActivity.REQUEST_GET_SINGLE_FILE
        )
    }

    private fun  openCamera(){
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(activity!!.packageManager)?.also {
                startActivityForResult(takePictureIntent, EditProfileActivity.REQUEST_CAPTURE_IMAGE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            EditProfileActivity.REQUEST_GET_SINGLE_FILE -> {
                if (resultCode == Activity.RESULT_OK && data != null){
                    val uri = data.data
                    try {
                        val bitmap = MediaStore.Images.Media.getBitmap(
                            context!!.contentResolver,
                            uri
                        )

                        val outputStream = ByteArrayOutputStream()
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)

                        selectedImageBytes = outputStream.toByteArray()

                        v.img_dialog.setImageBitmap(bitmap)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }
            }
            EditProfileActivity.REQUEST_CAPTURE_IMAGE -> {
                if (resultCode == Activity.RESULT_OK && data != null){
                    val imageBitmap = data.extras?.get("data") as Bitmap

                    val outputStream = ByteArrayOutputStream()
                    imageBitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)

                    selectedImageBytes = outputStream.toByteArray()

                    v.img_dialog.setImageBitmap(imageBitmap)
                }
            }
        }
    }
}