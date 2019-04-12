package com.iffy.fikhustaz.views.activity.editprof

import android.app.Activity
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.app.TimePickerDialog
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.view.View
import com.iffy.fikhustaz.R
import com.iffy.fikhustaz.data.AppConst
import com.iffy.fikhustaz.data.UserType
import com.iffy.fikhustaz.data.model.profile.Ustad
import com.iffy.fikhustaz.util.DatesFormat
import com.iffy.fikhustaz.views.activity.HomeActivity
import com.iffy.fikhustaz.views.activity.editprof.bottomsheetfragment.EditProfBottomSheetFragment
import kotlinx.android.synthetic.main.activity_edit_profile.*
import org.jetbrains.anko.*
import java.util.*
import android.util.Log.d
import java.io.IOException


class EditProfileActivity : AppCompatActivity(), EditProfContract.View {

    companion object {
        private const val REQUEST_GET_SINGLE_FILE = 101
        private const val REQUEST_CAPTURE_IMAGE = 102

    }

    private lateinit var presenter:EditProfPresenter
    private var imgUri: Uri? = null

    private lateinit var dialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val fighter = listOf("Camera", "Gallery")
        presenter = EditProfPresenter(this)
        presenter.getData()

        btn_add_schedule.setOnClickListener {
            val bottomSheetFragment = EditProfBottomSheetFragment()
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
        }
        date_tv_edit.setOnClickListener {
            showDatePicker(this)
        }

        save_btn_edit.setOnClickListener {
            tryToSaveData()
        }

        img_edit_prof.setOnClickListener {
            selector("Choose your fighter?", fighter, { dialogInterface, i ->
                when(fighter[i]){
                    "Camera" -> openCamera()
                    "Gallery" -> openGallery()
                }
            })
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
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        imgUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        val camIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        camIntent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri)
        startActivityForResult(camIntent, REQUEST_CAPTURE_IMAGE)

    }

    private fun tryToSaveData() {
        val name = name_tv_edit.text.toString()
        val tempat = datebirt_et_edit.text.toString()
        val tanggal = date_tv_edit.text.toString()
        val pendidikan = pendidikan_et_edit.text.toString()
        val keilmuan = keilmuan_et_edit.text.toString()
        val firkah = firkah_et_edit.text.toString()
        val mazhab = mazhab_et_edit.text.toString()

        presenter.saveData(
            Ustad(
                name, "", "", tempat, tanggal, pendidikan, keilmuan, firkah, mazhab, "", "", "", mutableListOf(), UserType.USTAZ,
                mutableListOf(), mutableListOf()
            )
        )
    }

    override fun setData(ustad: Ustad) {
        name_tv_edit.text = ustad.nama
        datebirt_et_edit.setText("${ustad.tempatLahir}")
        if (ustad.tanggalLahir == "" || ustad.tanggalLahir == null){
            date_tv_edit.text = "--/--/--"
        }else{
            date_tv_edit.text = ustad.tanggalLahir
        }
        pendidikan_et_edit.setText("${ustad.pendidikan}")
        keilmuan_et_edit.setText("${ustad.keilmuan}")
        mazhab_et_edit.setText("${ustad.mazhab}")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            REQUEST_GET_SINGLE_FILE -> {
                if (resultCode == Activity.RESULT_OK && data != null){
                    val uri = data.data
                    val imagePath = uri.path
                    d("EditProfile", imagePath)
                    try {
                        val bitmap = MediaStore.Images.Media.getBitmap(
                            contentResolver,
                            uri
                        )
                        // Log.d(TAG, String.valueOf(bitmap));
                        img_edit_prof.setImageBitmap(bitmap)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }
            }
            REQUEST_CAPTURE_IMAGE -> {
                if (resultCode == Activity.RESULT_OK && data != null){
                    img_edit_prof.setImageURI(imgUri)
                }
            }
        }
    }

    override fun showLoad() {
        dialog = ProgressDialog.show(this, "", "Tunggu sebentar")
        dialog.setCancelable(false)
        dialog.isIndeterminate
    }

    override fun hideLoad() {
        dialog.dismiss()
    }

    override fun showTimePicker(v: EditProfContract.View) {
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minut = c.get(Calendar.MINUTE)

        val datePickerDialog = TimePickerDialog(this@EditProfileActivity,
            TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->

            },hour, minut, false
        )
        datePickerDialog.show()
    }

    override fun showDatePicker(v: EditProfContract.View) {
        val calendar = Calendar.getInstance()
        val y = calendar.get(Calendar.YEAR)
        val m = calendar.get(Calendar.MONTH)
        val d = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this@EditProfileActivity,
            DatePickerDialog.OnDateSetListener { datePicker, i, i1, i2 ->
                val dob = i2.toString() + "/" + (i1 + 1) + "/" + i
                date_tv_edit.text = DatesFormat.reformatStringDate(dob, DatesFormat.DATE_FORMAT, DatesFormat.DATE_FORMAT_OUTPUT)
            }, y, m, d
        )
        datePickerDialog.show()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> {
                startActivity(intentFor<HomeActivity>("frg" to AppConst.EDIT_PROFILE_ACTIVITY).newTask().clearTask())
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        startActivity(intentFor<HomeActivity>("frg" to AppConst.EDIT_PROFILE_ACTIVITY).newTask().clearTask())
    }

    override fun showMsg(msg: String) {
        toast(msg)
    }

}
