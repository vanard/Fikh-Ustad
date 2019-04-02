package com.iffy.fikhustaz.views.activity.editprof.bottomsheetfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.iffy.fikhustaz.R
import kotlinx.android.synthetic.main.fragment_schedule_start.*
import org.jetbrains.anko.sdk27.coroutines.onCheckedChange
import org.jetbrains.anko.support.v4.toast
import java.util.*

class ScheduleStartFragment : Fragment() {

    private var day = Calendar.HOUR_OF_DAY
    private var minut = Calendar.MINUTE
    private var dayList : MutableList<String> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule_start, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        timePicker_start.setOnTimeChangedListener { view, hourOfDay, minute ->
            day = hourOfDay
            minut = minute
        }

        tv_save_start.setOnClickListener {
            toast("$day:$minut")
            if (cb_senin_day_start.isChecked){
                dayList.add("Senin")
            }
            if (cb_selasa_day_start.isChecked){
                dayList.add("Selasa")
            }
            if (cb_rabu_day_start.isChecked){
                dayList.add("Rabu")
            }
            if (cb_kamis_day_start.isChecked){
                dayList.add("Kamis")
            }
            if (cb_jumat_day_start.isChecked){
                dayList.add("Jumat")
            }
            if (cb_sabtu_day_start.isChecked){
                dayList.add("Sabtu")
            }
            if (cb_minggu_day_start.isChecked){
                dayList.add("Minggu")
            }
        }



    }

}
