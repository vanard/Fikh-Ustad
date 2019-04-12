package com.iffy.fikhustaz.views.activity.editprof.bottomsheetfragment


import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.iffy.fikhustaz.R
import com.iffy.fikhustaz.util.DatesFormat
import kotlinx.android.synthetic.main.fragment_schedule_manual.*
import java.util.*


class ScheduleManualFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule_manual, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        senin_timepicker_dialog.setOnClickListener {
            val calendarStart = Calendar.getInstance()
            val calendarEnd = Calendar.getInstance()

            val timePickerStart = TimePickerDialog(this@ScheduleManualFragment.context, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                val selectedTime = Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
                selectedTime.set(Calendar.MINUTE,minute)

                et_senin_fromone.setText(DatesFormat.dateToTime(selectedTime.time))
                et_senin_fromtwo.setText(DatesFormat.dateToTime(selectedTime.time))

                val timePickerEnd = TimePickerDialog(this@ScheduleManualFragment.context, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                    selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
                    selectedTime.set(Calendar.MINUTE,minute)

                    et_senin_toone.setText(DatesFormat.dateToTime(selectedTime.time))
                    et_senin_totwo.setText(DatesFormat.dateToTime(selectedTime.time))
                },
                    calendarEnd.get(Calendar.HOUR_OF_DAY), calendarEnd.get(Calendar.MINUTE),true)
                timePickerEnd.show()
            },
                calendarStart.get(Calendar.HOUR_OF_DAY), calendarStart.get(Calendar.MINUTE),true)
            timePickerStart.show()
        }

        selasa_timepicker_dialog.setOnClickListener {

        }

        rabu_timepicker_dialog.setOnClickListener {

        }

        kamis_timepicker_dialog.setOnClickListener {

        }

        jumat_timepicker_dialog.setOnClickListener {

        }

        sabtu_timepicker_dialog.setOnClickListener {

        }

        minggu_timepicker_dialog.setOnClickListener {

        }
    }

}
