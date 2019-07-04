package com.iffy.fikhustaz.views.fragment.materi.uploadmateri

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.iffy.fikhustaz.R
import com.iffy.fikhustaz.views.activity.editprof.EditProfileActivity.Companion.REQUEST_CAPTURE_IMAGE
import com.iffy.fikhustaz.views.activity.editprof.EditProfileActivity.Companion.REQUEST_GET_PDF_FILE
import com.iffy.fikhustaz.views.activity.editprof.EditProfileActivity.Companion.REQUEST_GET_SINGLE_FILE
import kotlinx.android.synthetic.main.activity_add_materi.*
import org.jetbrains.anko.selector
import org.jetbrains.anko.toast
import java.io.ByteArrayOutputStream
import java.io.IOException

class AddMateriActivity : AppCompatActivity(), AddMateriContract.View {

    private var mFile : Uri? = null
    private var selectedImageBytes : ByteArray? = null
    private lateinit var presenter: AddMateriPresenter
    private lateinit var dialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_materi)

        presenter = AddMateriPresenter(this)
        val fighter = listOf("Camera", "Gallery")

        add_materi_file.setOnClickListener {
            openFiles()
        }
        add_materi_image.setOnClickListener {
            selector("Choose option", fighter) { dialogInterface, i ->
                when(fighter[i]){
                    "Camera" -> openCamera()
                    "Gallery" -> openGallery()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_GET_PDF_FILE -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    add_materi_file.text = "Ganti File"
                    mFile = data.data
                }
            }
            REQUEST_GET_SINGLE_FILE -> {
                if (resultCode == Activity.RESULT_OK && data != null){
                    val uri = data.data
                    try {
                        val bitmap = MediaStore.Images.Media.getBitmap(
                            contentResolver,
                            uri
                        )

                        val outputStream = ByteArrayOutputStream()
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)

                        selectedImageBytes = outputStream.toByteArray()

                        add_materi_image.setImageBitmap(bitmap)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }
            }
            REQUEST_CAPTURE_IMAGE -> {
                if (resultCode == Activity.RESULT_OK && data != null){
                    val imageBitmap = data.extras?.get("data") as Bitmap

                    val outputStream = ByteArrayOutputStream()
                    imageBitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)

                    selectedImageBytes = outputStream.toByteArray()

                    add_materi_image.setImageBitmap(imageBitmap)
                }
            }
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
        startActivityForResult(
            Intent.createChooser(intent, "Select Picture"),
            REQUEST_GET_SINGLE_FILE
        )
    }

    private fun  openCamera(){
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_CAPTURE_IMAGE)
            }
        }
    }

    private fun openFiles() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "application/pdf"
        startActivityForResult(
            Intent.createChooser(intent, "Select File"),
            REQUEST_GET_PDF_FILE
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_materi_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> {
                finish()
            }
            R.id.menu_publish -> {
                presenter.uploadMateri(FirebaseAuth.getInstance().currentUser?.uid, add_materi_title.text.toString().toLowerCase(),
                    mFile, selectedImageBytes)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showLoad() {
        dialog = ProgressDialog.show(this, "", "Tunggu sebentar")
        dialog.setCancelable(false)
        dialog.isIndeterminate
    }

    override fun hideLoad() {
        dialog.dismiss()
        finish()
    }

    override fun showMsg(msg: String) {
        toast(msg)
    }
}
