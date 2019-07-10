package com.iffy.fikhustaz.views.activity.editprof.upload

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.iffy.fikhustaz.R
import com.iffy.fikhustaz.data.StatusAccount
import com.iffy.fikhustaz.util.FirebaseUtil
import com.iffy.fikhustaz.views.activity.editprof.EditProfileActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.dialog_upload.view.*
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.selector
import org.jetbrains.anko.support.v4.toast
import java.io.ByteArrayOutputStream
import java.io.IOException



class DialogUploadFragment: DialogFragment(){

    private var selectedImageBytes: ByteArray? = null
    lateinit var v : View
    lateinit var upImageRef : StorageReference

    companion object{
        private val ARG_CAUGHT = "myFragment_caught"

        fun newInstance(type: String):DialogUploadFragment{
            val args: Bundle = Bundle()
            args.putString(ARG_CAUGHT, type)
            val fragment = DialogUploadFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.dialog_upload, container, false)

        val fighter = listOf("Camera", "Gallery")
        val tipe = arguments?.getString(ARG_CAUGHT)

        v.img_dialog.setOnClickListener {
            context!!.selector("Choose your option?", fighter) { dialogInterface, i ->
                when(fighter[i]){
                    "Camera" -> openCamera()
                    "Gallery" -> openGallery()
                }
            }
        }
        v.btn_pick_dialog.setOnClickListener {
            context!!.selector("Choose your option?", fighter) { dialogInterface, i ->
                when(fighter[i]){
                    "Camera" -> openCamera()
                    "Gallery" -> openGallery()
                }
            }
        }

        if (tipe != null) {
            loadPage(tipe)

            val user = FirebaseAuth.getInstance().currentUser

            upImageRef =
                FirebaseStorage.getInstance().getReference("${user?.uid}/$tipe/${user?.uid}")

            v.btn_upload_dialog.setOnClickListener {

                if (selectedImageBytes != null) {
                    val mDialg = ProgressDialog.show(context, "Uploading", "Tunggu sebentar")
                    mDialg.setCancelable(false)
                    mDialg.isIndeterminate
                    upImageRef.putBytes(selectedImageBytes!!).addOnCompleteListener {
                        upImageRef.downloadUrl.addOnSuccessListener { uri ->
                            if (tipe == "profilePicture"){
                                val user = FirebaseAuth.getInstance().currentUser
                                if (uri != null) {
                                    val profile = UserProfileChangeRequest.Builder()
                                        .setPhotoUri(uri)
                                        .build()

                                    user!!.updateProfile(profile)
                                }
                            }
                            val userFieldMap = mutableMapOf<String, Any>()
                            userFieldMap[tipe] = uri.toString()
                            userFieldMap["verified"] = StatusAccount.PENDING
                            FirebaseUtil.currentUserDocRef.update(userFieldMap)

                            toast("Upload success")

                            dialog?.dismiss()
                            mDialg.dismiss()
                        }
                    }
                }else{
                    toast("Pilih gambar terlebih dahulu")
                }
            }
        }

        return v
    }

    private fun loadPage(tipe: String) {
        val mDial = ProgressDialog.show(context, "", "Tunggu sebentar")
        mDial.setCancelable(false)
        mDial.isIndeterminate

        FirebaseUtil.getCurrentUser {
            if (tipe == "ijazah"){
                if (it.ijazah != null && it.ijazah.isNotBlank())
                    Picasso.get().load(it.ijazah).into(v.img_dialog)
            }else if (tipe == "sertifikat"){
                if (it.sertifikat != null && it.sertifikat.isNotBlank())
                    Picasso.get().load(it.sertifikat).into(v.img_dialog)
            }else if (tipe == "profilePicture"){
                if (it.profilePicture != null && it.profilePicture.isNotBlank())
                    Picasso.get().load(it.profilePicture).into(v.img_dialog)
            }
            mDial.dismiss()
        }
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
                        v.btn_pick_dialog.backgroundResource = R.drawable.bg_chip
                        v.btn_pick_dialog.text = "Ganti Gambar"
                        v.btn_pick_dialog.setTextColor(Color.GRAY)

                        v.btn_upload_dialog.backgroundResource = R.drawable.btn_green_register
                        v.btn_upload_dialog.setTextColor(Color.WHITE)
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
                    v.btn_pick_dialog.backgroundResource = R.drawable.bg_chip
                    v.btn_pick_dialog.text = "Ganti Gambar"
                    v.btn_pick_dialog.setTextColor(Color.GRAY)

                    v.btn_upload_dialog.backgroundResource = R.drawable.btn_green_register
                    v.btn_upload_dialog.setTextColor(Color.WHITE)
                }
            }
        }
    }
}