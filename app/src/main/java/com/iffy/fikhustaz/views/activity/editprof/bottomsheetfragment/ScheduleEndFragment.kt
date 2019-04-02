package com.iffy.fikhustaz.views.activity.editprof.bottomsheetfragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.iffy.fikhustaz.R
import kotlinx.android.synthetic.main.fragment_schedule_end.*
import org.jetbrains.anko.sdk27.coroutines.onCheckedChange
import org.jetbrains.anko.support.v4.toast
import java.util.*

class ScheduleEndFragment : Fragment() {

    private var day = Calendar.HOUR_OF_DAY
    private var minut = Calendar.MINUTE
    private var dayList : MutableList<String> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule_end, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        timePicker_end.setOnTimeChangedListener { view, hourOfDay, minute ->
            day = hourOfDay
            minut = minute
        }

        tv_save_end.setOnClickListener {
            toast("$day:$minut")
            if (cb_senin_day_end.isChecked){
                dayList.add("Senin")
            }
            if (cb_selasa_day_end.isChecked){
                dayList.add("Selasa")
            }
            if (cb_rabu_day_end.isChecked){
                dayList.add("Rabu")
            }
            if (cb_kamis_day_end.isChecked){
                dayList.add("Kamis")
            }
            if (cb_jumat_day_end.isChecked){
                dayList.add("Jumat")
            }
            if (cb_sabtu_day_end.isChecked){
                dayList.add("Sabtu")
            }
            if (cb_minggu_day_end.isChecked){
                dayList.add("Minggu")
            }
            toast(dayList.toString())
        }
    }

}
