package com.iffy.fikhustaz.views.activity.editprof

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.iffy.fikhustaz.R
import com.iffy.fikhustaz.data.model.Ustad
import com.iffy.fikhustaz.util.DatePickerFragment
import com.iffy.fikhustaz.util.TimePickerFragment
import com.iffy.fikhustaz.views.activity.editprof.bottomsheetfragment.EditProfBottomSheetFragment
import kotlinx.android.synthetic.main.activity_edit_profile.*

class EditProfileActivity : AppCompatActivity(), EditProfContract.View {

    private lateinit var presenter:EditProfPresenter

    override fun showLoad() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoad() {
        progressBar.visibility = View.GONE
    }

    override fun showTimePicker(v: EditProfContract.View) {
        TimePickerFragment().show(supportFragmentManager, "timePicker")
    }

    override fun showDatePicker(v: EditProfContract.View) {
        DatePickerFragment().show(supportFragmentManager, "datePicker")
    }

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

    }

    private fun tryToSaveData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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

}
