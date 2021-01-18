package com.iffy.fikhustaz.views.activity.editprof

import android.app.Activity
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.app.TimePickerDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.iffy.fikhustaz.R
import com.iffy.fikhustaz.data.AppConst
import com.iffy.fikhustaz.data.UserType
import com.iffy.fikhustaz.data.itemviews.ScheduleItem
import com.iffy.fikhustaz.data.model.profile.ItOnline
import com.iffy.fikhustaz.data.model.profile.ItSchedule
import com.iffy.fikhustaz.data.model.profile.Ustad
import com.iffy.fikhustaz.util.DatesFormat
import com.iffy.fikhustaz.util.FirebaseUtil
import com.iffy.fikhustaz.views.activity.HomeActivity
import com.iffy.fikhustaz.views.activity.editprof.bottomsheetfragment.EditProfBottomSheetFragment
import com.iffy.fikhustaz.views.activity.editprof.upload.VerifyActivity
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.activity_edit_profile.*
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onCheckedChange
import java.util.*
import java.io.ByteArrayOutputStream
import java.io.IOException


class EditProfileActivity : AppCompatActivity(), EditProfContract.View {

    private val TAG = "EditProfileActivity"

    companion object {
        const val REQUEST_GET_SINGLE_FILE = 101
        const val REQUEST_CAPTURE_IMAGE = 102
        const val REQUEST_GET_PDF_FILE = 103
    }

    private lateinit var presenter:EditProfPresenter
    private var selectedImageBytes: ByteArray? = null

    private lateinit var dialog: ProgressDialog

    private var mSchedule = mutableListOf<Item>()
    private var mScheduleList = mutableListOf<ItSchedule>()

    lateinit var bottomSheetFragment: EditProfBottomSheetFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val fighter = listOf("Camera", "Gallery")
        presenter = EditProfPresenter(this)
        presenter.getData()

        rv_edit_schedule.layoutManager = GridLayoutManager(this@EditProfileActivity, 3)

        img_edit_schedule.setOnClickListener {
            bottomSheetFragment = EditProfBottomSheetFragment()
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
        }
        date_tv_edit.setOnClickListener {
            showDatePicker(this)
        }

        img_edit_prof.setOnClickListener {
            selector("Choose your option?", fighter) { dialogInterface, i ->
                when(fighter[i]){
                    "Camera" -> openCamera()
                    "Gallery" -> openGallery()
                }
            }
        }

        sw_status_edit.onCheckedChange { buttonView, isChecked ->
            if (isChecked){
                sw_status_edit.text = "Online"
            }else{
                sw_status_edit.text = "Offline"
            }
        }

        loadData()

        btn_upload_edit_prof.setOnClickListener {
           startActivity<VerifyActivity>()
        }

    }

    fun loadData(code: Int? = 0) {
        FirebaseUtil.getCurrentUser {
            if (it.ijazah != null && it.ijazah.isNotBlank() &&
                it.sertifikat != null && it.sertifikat.isNotBlank() &&
                it.profilePicture != null && it.profilePicture.isNotBlank()) {
                    btn_upload_edit_prof.text = "Ubah persyaratan data?"
            }
            if (code != 0) {
                mScheduleList.clear()
                mSchedule.clear()
                this.setData(it)
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
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_CAPTURE_IMAGE)
        } catch (e: ActivityNotFoundException) {
            // display error state to the user
            Log.d(TAG, "openCamera: something went wrong")
        }
//        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
//            takePictureIntent.resolveActivity(packageManager)?.also {
//                startActivityForResult(takePictureIntent, REQUEST_CAPTURE_IMAGE)
//            }
//        }
    }

    private fun tryToSaveData() {
        val name = name_et_edit.text.toString()
        val tempat = datebirt_et_edit.text.toString()
        val tanggal = date_tv_edit.text.toString()
        val pendidikan = pendidikan_et_edit.text.toString()
        val keilmuan = keilmuan_et_edit.text.toString()
        val mazhab = mazhab_et_edit.text.toString()
        val userOnline: MutableList<ItOnline> = mutableListOf()

        if (sw_status_edit.isChecked){
            userOnline.clear()
            userOnline.add(ItOnline("Online", System.currentTimeMillis()))
        }else{
            userOnline.clear()
            userOnline.add(ItOnline("Offline", System.currentTimeMillis()))
        }

        presenter.saveData(
            Ustad(
                name, "", "", tempat, tanggal, pendidikan, keilmuan, mazhab, selectedImageBytes.toString(),"", "", null, null, null, UserType.USTAZ,
                userOnline, mutableListOf()
            ),
            selectedImageBytes
        )
    }

    override fun setData(ustad: Ustad) {
        if (FirebaseAuth.getInstance().currentUser?.photoUrl != null) {
            Picasso.get()
                .load(FirebaseAuth.getInstance().currentUser!!.photoUrl.toString())
                .into(img_edit_prof)
        }

        if (!ustad.userOnline.isNullOrEmpty()){
            if (ustad.userOnline[0].status == "Online"){
                sw_status_edit.text = "Online"
                sw_status_edit.isChecked = true
            }else{
                sw_status_edit.text = "Offline"
                sw_status_edit.isChecked = false
            }

        }else{
            sw_status_edit.text = "Offline"
            sw_status_edit.isChecked = false
        }

        name_et_edit.setText(ustad.nama)
        datebirt_et_edit.setText("${ustad.tempatLahir}")
        if (ustad.tanggalLahir == "" || ustad.tanggalLahir == null){
            date_tv_edit.text = "--/--/--"
        }else{
            date_tv_edit.text = ustad.tanggalLahir
        }
        pendidikan_et_edit.setText("${ustad.pendidikan}")
        keilmuan_et_edit.setText("${ustad.keilmuan}")
        mazhab_et_edit.setText("${ustad.mazhab}")

        if (ustad.schedule != null){
            mScheduleList.addAll(ustad.schedule)
        }

        if (mScheduleList.isNotEmpty()){
            mScheduleList.forEach {
                mSchedule.add(ScheduleItem(it))
            }

            rv_edit_schedule.apply {
                adapter = GroupAdapter<ViewHolder>().apply {
                    addAll(mSchedule)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
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

                        img_edit_prof.setImageBitmap(bitmap)
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

                    img_edit_prof.setImageBitmap(imageBitmap)
                }
            }
        }
    }

    override fun showLoad() {
        dialog = ProgressDialog.show(this, "", "Please wait...")
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.edit_profile_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                startActivity(intentFor<HomeActivity>("frg" to AppConst.EDIT_PROFILE_ACTIVITY).newTask().clearTask())
                return true
            }
            R.id.menu_save_profile -> {
                tryToSaveData()
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

    override fun onSuccess() {
        startActivity(intentFor<HomeActivity>().newTask().clearTask())
    }

}
