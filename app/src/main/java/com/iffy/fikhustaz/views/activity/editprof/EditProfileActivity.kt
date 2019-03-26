package com.iffy.fikhustaz.views.activity.editprof

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.iffy.fikhustaz.R
import com.iffy.fikhustaz.data.AppConst
import com.iffy.fikhustaz.data.UserType
import com.iffy.fikhustaz.data.model.Ustad
import com.iffy.fikhustaz.util.DatesFormat
import com.iffy.fikhustaz.views.activity.HomeActivity
import com.iffy.fikhustaz.views.activity.editprof.bottomsheetfragment.EditProfBottomSheetFragment
import kotlinx.android.synthetic.main.activity_edit_profile.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import org.jetbrains.anko.toast
import java.util.*

class EditProfileActivity : AppCompatActivity(), EditProfContract.View {

    private lateinit var presenter:EditProfPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

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

        img_detailutd_edit.setOnClickListener {

        }

    }

    private fun tryToSaveData() {
        val name = name_tv_edit.text.toString()
        val tempat = datebirt_et_edit.text.toString()
        val tanggal = date_tv_edit.text.toString()
        val pendidikan = pendidikan_et_edit.text.toString()
        val keilmuan = keilmuan_et_edit.text.toString()
        val firkah = firkah_et_edit.text.toString()
        val mazhab = mazhab_et_edit.text.toString()

        presenter.saveData(Ustad(name, "","",tempat, tanggal, pendidikan, keilmuan, firkah, mazhab,"","","","",UserType.USTAZ,
            mutableListOf(), mutableListOf()))
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
        firkah_et_edit.setText("${ustad.firkah}")
        mazhab_et_edit.setText("${ustad.mazhab}")
    }

    override fun showLoad() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoad() {
        progressBar.visibility = View.GONE
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
