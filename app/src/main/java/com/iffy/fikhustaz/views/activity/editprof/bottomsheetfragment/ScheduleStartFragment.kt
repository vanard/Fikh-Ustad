package com.iffy.fikhustaz.views.activity.editprof.bottomsheetfragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.iffy.fikhustaz.R
import com.iffy.fikhustaz.data.model.profile.ItemSchedule
import com.iffy.fikhustaz.util.DatesFormat
import com.iffy.fikhustaz.views.activity.editprof.addschedule.AddScheduleActivity
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.fragment_schedule_start.*
import org.jetbrains.anko.sdk27.coroutines.onCheckedChange
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import java.util.*

class ScheduleStartFragment : Fragment() {

    private var second: Long = 0
    private lateinit var calendar: Calendar
    private var dayList : MutableList<ItemSchedule> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule_start, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        calendar = Calendar.getInstance()

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

        btn_manual_schedule_start.setOnClickListener {
            startActivity<AddScheduleActivity>()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        timePicker_start.setOnTimeChangedListener { view, hourOfDay, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
            calendar.set(Calendar.MINUTE, minute)
            second = calendar.timeInMillis/1000
        }

        tv_save_start.setOnClickListener {
            dayList.clear()

            if (cb_senin_day_start.isChecked){
                dayList.add(ItemSchedule("Senin",DatesFormat.secondToDate(second)))
            }
            if (cb_selasa_day_start.isChecked){
                dayList.add(ItemSchedule("Selasa",DatesFormat.secondToDate(second)))
            }
            if (cb_rabu_day_start.isChecked){
                dayList.add(ItemSchedule("Rabu",DatesFormat.secondToDate(second)))
            }
            if (cb_kamis_day_start.isChecked){
                dayList.add(ItemSchedule("Kamis",DatesFormat.secondToDate(second)))
            }
            if (cb_jumat_day_start.isChecked){
                dayList.add(ItemSchedule("Jumat",DatesFormat.secondToDate(second)))
            }
            if (cb_sabtu_day_start.isChecked){
                dayList.add(ItemSchedule("Sabtu",DatesFormat.secondToDate(second)))
            }
            if (cb_minggu_day_start.isChecked){
                dayList.add(ItemSchedule("Minggu",DatesFormat.secondToDate(second)))
            }
            if (dayList.isEmpty()){
                toast("Pilih hari terlebih dahulu")
            }else{
                toast(DatesFormat.secondToDate(second))
                Log.d("ScheduleStart", dayList.toString())
            }
        }



    }

}
