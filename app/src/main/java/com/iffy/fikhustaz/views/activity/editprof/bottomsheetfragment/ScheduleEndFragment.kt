package com.iffy.fikhustaz.views.activity.editprof.bottomsheetfragment


import android.os.Bundle
import android.util.Log.d
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.iffy.fikhustaz.R
import com.iffy.fikhustaz.data.model.profile.ItemSchedule
import com.iffy.fikhustaz.util.DatesFormat
import com.iffy.fikhustaz.views.activity.editprof.addschedule.AddScheduleActivity
import kotlinx.android.synthetic.main.fragment_schedule_end.*
import org.jetbrains.anko.sdk27.coroutines.onCheckedChange
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import java.util.*



class ScheduleEndFragment : Fragment() {

    private var second: Long = 0
    private lateinit var calendar: Calendar
    private var dayList : MutableList<ItemSchedule> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule_end, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        calendar = Calendar.getInstance()

        cb_everyday_end.onCheckedChange { buttonView, isChecked ->
            if (isChecked){
                cb_senin_day_end.isChecked = isChecked
                cb_selasa_day_end.isChecked = isChecked
                cb_rabu_day_end.isChecked = isChecked
                cb_kamis_day_end.isChecked = isChecked
                cb_jumat_day_end.isChecked = isChecked
                cb_sabtu_day_end.isChecked = isChecked
                cb_minggu_day_end.isChecked = isChecked
            }else{
                cb_senin_day_end.isChecked = false
                cb_selasa_day_end.isChecked = false
                cb_rabu_day_end.isChecked = false
                cb_kamis_day_end.isChecked = false
                cb_jumat_day_end.isChecked = false
                cb_sabtu_day_end.isChecked = false
                cb_minggu_day_end.isChecked = false
            }
        }

        btn_manual_schedule_end.setOnClickListener {
            startActivity<AddScheduleActivity>()
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        timePicker_end.setOnTimeChangedListener { view, hourOfDay, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
            calendar.set(Calendar.MINUTE, minute)
            second = calendar.timeInMillis/1000
        }

        tv_save_end.setOnClickListener {
            dayList.clear()
            if (cb_senin_day_end.isChecked){
                dayList.add(ItemSchedule("Senin",DatesFormat.secondToDate(second)))
            }
            if (cb_selasa_day_end.isChecked){
                dayList.add(ItemSchedule("Selasa",DatesFormat.secondToDate(second)))
            }
            if (cb_rabu_day_end.isChecked){
                dayList.add(ItemSchedule("Rabu",DatesFormat.secondToDate(second)))
            }
            if (cb_kamis_day_end.isChecked){
                dayList.add(ItemSchedule("Kamis",DatesFormat.secondToDate(second)))
            }
            if (cb_jumat_day_end.isChecked){
                dayList.add(ItemSchedule("Jumat",DatesFormat.secondToDate(second)))
            }
            if (cb_sabtu_day_end.isChecked){
                dayList.add(ItemSchedule("Sabtu",DatesFormat.secondToDate(second)))
            }
            if (cb_minggu_day_end.isChecked){
                dayList.add(ItemSchedule("Minggu",DatesFormat.secondToDate(second)))
            }

            if (dayList.isEmpty()){
                toast("Pilih hari terlebih dahulu")
            }else{
                toast(DatesFormat.secondToDate(second))
                d("ScheduleEnd",dayList.toString())
            }

        }
    }

}
