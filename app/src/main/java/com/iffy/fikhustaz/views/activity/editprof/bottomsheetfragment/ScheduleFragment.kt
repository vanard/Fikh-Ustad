package com.iffy.fikhustaz.views.activity.editprof.bottomsheetfragment

import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.iffy.fikhustaz.R
import com.iffy.fikhustaz.data.model.profile.ItSchedule
import com.iffy.fikhustaz.util.DatesFormat
import com.iffy.fikhustaz.util.FirebaseUtil
import kotlinx.android.synthetic.main.fragment_schedule_start.*
import org.jetbrains.anko.sdk27.coroutines.onCheckedChange
import org.jetbrains.anko.support.v4.toast
import java.util.*

class ScheduleFragment : Fragment() {

    private lateinit var calendarStart: Calendar
    private lateinit var calendarEnd: Calendar
    private var dayList : MutableList<ItSchedule> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule_start, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tv_time_start.text = DatesFormat.secondToTime(Calendar.getInstance(Locale.getDefault()).timeInMillis/1000L)
        tv_time_end.text = DatesFormat.secondToTime(Calendar.getInstance(Locale.getDefault()).timeInMillis/1000L)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        cb_everyday_start.onCheckedChange { buttonView, isChecked ->
            if (isChecked){
                cb_senin_day_start.isChecked = isChecked
                cb_selasa_day_start.isChecked = isChecked
                cb_rabu_day_start.isChecked = isChecked
                cb_kamis_day_start.isChecked = isChecked
                cb_jumat_day_start.isChecked = isChecked
                cb_sabtu_day_start.isChecked = isChecked
                cb_minggu_day_start.isChecked = isChecked
            }else{
                cb_senin_day_start.isChecked = false
                cb_selasa_day_start.isChecked = false
                cb_rabu_day_start.isChecked = false
                cb_kamis_day_start.isChecked = false
                cb_jumat_day_start.isChecked = false
                cb_sabtu_day_start.isChecked = false
                cb_minggu_day_start.isChecked = false
            }
        }

        schedule_start_linear.setOnClickListener {
            calendarStart = Calendar.getInstance()

            val timePicker = TimePickerDialog(this@ScheduleFragment.context, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                val selectedTime = Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
                selectedTime.set(Calendar.MINUTE,minute)
                tv_time_start.text = DatesFormat.dateToTime(selectedTime.time)
            },
                calendarStart.get(Calendar.HOUR_OF_DAY), calendarStart.get(Calendar.MINUTE),true)
            timePicker.show()
        }

        schedule_end_linear.setOnClickListener {
            calendarEnd = Calendar.getInstance()

            val timePicker = TimePickerDialog(this@ScheduleFragment.context, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                val selectedTime = Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
                selectedTime.set(Calendar.MINUTE,minute)
                tv_time_end.text = DatesFormat.dateToTime(selectedTime.time)
            },
                calendarEnd.get(Calendar.HOUR_OF_DAY), calendarEnd.get(Calendar.MINUTE),true)
            timePicker.show()
        }

        tv_save_start.setOnClickListener {
            dayList.clear()

            if (cb_senin_day_start.isChecked){
                dayList.add(ItSchedule("Senin",tv_time_start.text.toString(), tv_time_end.text.toString()))
            }
            if (cb_selasa_day_start.isChecked){
                dayList.add(ItSchedule("Selasa",tv_time_start.text.toString(), tv_time_end.text.toString()))
            }
            if (cb_rabu_day_start.isChecked){
                dayList.add(ItSchedule("Rabu",tv_time_start.text.toString(), tv_time_end.text.toString()))
            }
            if (cb_kamis_day_start.isChecked){
                dayList.add(ItSchedule("Kamis",tv_time_start.text.toString(), tv_time_end.text.toString()))
            }
            if (cb_jumat_day_start.isChecked){
                dayList.add(ItSchedule("Jumat",tv_time_start.text.toString(), tv_time_end.text.toString()))
            }
            if (cb_sabtu_day_start.isChecked){
                dayList.add(ItSchedule("Sabtu",tv_time_start.text.toString(), tv_time_end.text.toString()))
            }
            if (cb_minggu_day_start.isChecked){
                dayList.add(ItSchedule("Minggu",tv_time_start.text.toString(), tv_time_end.text.toString()))
            }
            if (dayList.isEmpty()){
                toast("Pilih hari terlebih dahulu")
            }else{
                FirebaseUtil.updateCurrentUser("","","","","","","","","","","","",dayList)
                toast("Schedule Updated")
            }
        }
    }
}
