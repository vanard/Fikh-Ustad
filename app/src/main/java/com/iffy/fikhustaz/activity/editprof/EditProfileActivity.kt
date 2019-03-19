package com.iffy.fikhustaz.activity.editprof

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.iffy.fikhustaz.R
import com.iffy.fikhustaz.util.DatePickerFragment
import com.iffy.fikhustaz.util.TimePickerFragment
import kotlinx.android.synthetic.main.activity_edit_profile.*

class EditProfileActivity : AppCompatActivity(), EditProfContract.View {

    private lateinit var presenter:EditProfPresenter

    override fun showLoad() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoad() {
        progressBar.visibility = View.VISIBLE
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
        btn_add_schedule.setOnClickListener {
            showTimePicker(this)
        }
        date_tv_edit.setOnClickListener {
            showDatePicker(this)
        }

    }
}
