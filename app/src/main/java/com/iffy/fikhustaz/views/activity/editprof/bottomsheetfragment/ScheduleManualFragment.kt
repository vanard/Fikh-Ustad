package com.iffy.fikhustaz.views.activity.editprof.bottomsheetfragment


import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.iffy.fikhustaz.R
import com.iffy.fikhustaz.data.model.profile.ItSchedule
import com.iffy.fikhustaz.data.model.profile.Ustad
import com.iffy.fikhustaz.util.DatesFormat
import com.iffy.fikhustaz.util.FirebaseUtil
import kotlinx.android.synthetic.main.fragment_schedule_manual.*
import org.jetbrains.anko.support.v4.toast
import java.util.*


class ScheduleManualFragment : Fragment() {

    private var mUstad: Ustad? = null
    private var dayList : MutableList<ItSchedule> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule_manual, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepare()

        senin_timepicker_dialog.setOnClickListener {
            seninPickerDialog()
        }

        selasa_timepicker_dialog.setOnClickListener {
            selasaPickerDialog()
        }

        rabu_timepicker_dialog.setOnClickListener {
            rabuPickerDialog()
        }

        kamis_timepicker_dialog.setOnClickListener {
            kamisPickerDialog()
        }

        jumat_timepicker_dialog.setOnClickListener {
            jumatPickerDialog()
        }

        sabtu_timepicker_dialog.setOnClickListener {
            sabtuPickerDialog()
        }

        minggu_timepicker_dialog.setOnClickListener {
            mingguPickerDialog()
        }

        btn_save_manual_schedule.setOnClickListener { saveSchedule() }
    }

    private fun saveSchedule() {
        dayList.clear()

        if (cb_senin.isChecked){
            dayList.add(ItSchedule("Senin","${et_senin_fromone.text}:${et_senin_fromtwo.text}", "${et_senin_toone.text}:${et_senin_totwo.text}"))
        }
        if (cb_selasa.isChecked){
            dayList.add(ItSchedule("Selasa","${et_selasa_fromone.text}:${et_selasa_fromtwo.text}", "${et_selasa_toone.text}:${et_selasa_totwo.text}"))
        }
        if (cb_rabu.isChecked){
            dayList.add(ItSchedule("Rabu","${et_rabu_fromone.text}:${et_rabu_fromtwo.text}", "${et_rabu_toone.text}:${et_rabu_totwo.text}"))
        }
        if (cb_kamis.isChecked){
            dayList.add(ItSchedule("Kamis","${et_kamis_fromone.text}:${et_kamis_fromtwo.text}", "${et_kamis_toone.text}:${et_kamis_totwo.text}"))
        }
        if (cb_jumat.isChecked){
            dayList.add(ItSchedule("Jumat","${et_jumat_fromone.text}:${et_jumat_fromtwo.text}", "${et_jumat_toone.text}:${et_jumat_totwo.text}"))
        }
        if (cb_sabtu.isChecked){
            dayList.add(ItSchedule("Sabtu","${et_sabtu_fromone.text}:${et_sabtu_fromtwo.text}", "${et_sabtu_toone.text}:${et_sabtu_totwo.text}"))
        }
        if (cb_minggu.isChecked){
            dayList.add(ItSchedule("Minggu","${et_minggu_fromone.text}:${et_minggu_fromtwo.text}", "${et_minggu_toone.text}:${et_minggu_totwo.text}"))
        }
        if (dayList.isEmpty()){
            toast("Pilih hari terlebih dahulu")
        }else{
            FirebaseUtil.updateCurrentUser("","","","","","","","","","","","",dayList)
            toast("Jadwal berhasil diubah")
        }
    }

    private fun seninPickerDialog() {
        val calendarStart = Calendar.getInstance()
        val calendarEnd = Calendar.getInstance()

        val timePickerStart = TimePickerDialog(this@ScheduleManualFragment.context, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            val selectedTime = Calendar.getInstance()
            selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
            selectedTime.set(Calendar.MINUTE,minute)

            var time = DatesFormat.dateToTime(selectedTime.time).split(":")

            et_senin_fromone.setText(time[0])
            et_senin_fromtwo.setText(time[1])

            val timePickerEnd = TimePickerDialog(this@ScheduleManualFragment.context, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
                selectedTime.set(Calendar.MINUTE,minute)

                time = DatesFormat.dateToTime(selectedTime.time).split(":")

                et_senin_toone.setText(time[0])
                et_senin_totwo.setText(time[1])

                cb_senin.isChecked = true
            },
                calendarEnd.get(Calendar.HOUR_OF_DAY), calendarEnd.get(Calendar.MINUTE),true)
            timePickerEnd.show()
        },
            calendarStart.get(Calendar.HOUR_OF_DAY), calendarStart.get(Calendar.MINUTE),true)
        timePickerStart.show()
    }

    private fun selasaPickerDialog(){
        val calendarStart = Calendar.getInstance()
        val calendarEnd = Calendar.getInstance()

        val timePickerStart = TimePickerDialog(this@ScheduleManualFragment.context, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            val selectedTime = Calendar.getInstance()
            selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
            selectedTime.set(Calendar.MINUTE,minute)

            var time = DatesFormat.dateToTime(selectedTime.time).split(":")

            et_selasa_fromone.setText(time[0])
            et_selasa_fromtwo.setText(time[1])

            val timePickerEnd = TimePickerDialog(this@ScheduleManualFragment.context, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
                selectedTime.set(Calendar.MINUTE,minute)

                time = DatesFormat.dateToTime(selectedTime.time).split(":")

                et_selasa_toone.setText(time[0])
                et_selasa_totwo.setText(time[1])

                cb_selasa.isChecked = true
            },
                calendarEnd.get(Calendar.HOUR_OF_DAY), calendarEnd.get(Calendar.MINUTE),true)
            timePickerEnd.show()
        },
            calendarStart.get(Calendar.HOUR_OF_DAY), calendarStart.get(Calendar.MINUTE),true)
        timePickerStart.show()
    }

    private fun rabuPickerDialog(){
        val calendarStart = Calendar.getInstance()
        val calendarEnd = Calendar.getInstance()

        val timePickerStart = TimePickerDialog(this@ScheduleManualFragment.context, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            val selectedTime = Calendar.getInstance()
            selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
            selectedTime.set(Calendar.MINUTE,minute)

            var time = DatesFormat.dateToTime(selectedTime.time).split(":")

            et_rabu_fromone.setText(time[0])
            et_rabu_fromtwo.setText(time[1])

            val timePickerEnd = TimePickerDialog(this@ScheduleManualFragment.context, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
                selectedTime.set(Calendar.MINUTE,minute)

                time = DatesFormat.dateToTime(selectedTime.time).split(":")

                et_rabu_toone.setText(time[0])
                et_rabu_totwo.setText(time[1])

                cb_rabu.isChecked = true
            },
                calendarEnd.get(Calendar.HOUR_OF_DAY), calendarEnd.get(Calendar.MINUTE),true)
            timePickerEnd.show()
        },
            calendarStart.get(Calendar.HOUR_OF_DAY), calendarStart.get(Calendar.MINUTE),true)
        timePickerStart.show()
    }

    private fun kamisPickerDialog(){
        val calendarStart = Calendar.getInstance()
        val calendarEnd = Calendar.getInstance()

        val timePickerStart = TimePickerDialog(this@ScheduleManualFragment.context, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            val selectedTime = Calendar.getInstance()
            selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
            selectedTime.set(Calendar.MINUTE,minute)

            var time = DatesFormat.dateToTime(selectedTime.time).split(":")

            et_kamis_fromone.setText(time[0])
            et_kamis_fromtwo.setText(time[1])

            val timePickerEnd = TimePickerDialog(this@ScheduleManualFragment.context, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
                selectedTime.set(Calendar.MINUTE,minute)

                time = DatesFormat.dateToTime(selectedTime.time).split(":")

                et_kamis_toone.setText(time[0])
                et_kamis_totwo.setText(time[1])

                cb_kamis.isChecked = true
            },
                calendarEnd.get(Calendar.HOUR_OF_DAY), calendarEnd.get(Calendar.MINUTE),true)
            timePickerEnd.show()
        },
            calendarStart.get(Calendar.HOUR_OF_DAY), calendarStart.get(Calendar.MINUTE),true)
        timePickerStart.show()
    }

    private fun jumatPickerDialog(){
        val calendarStart = Calendar.getInstance()
        val calendarEnd = Calendar.getInstance()

        val timePickerStart = TimePickerDialog(this@ScheduleManualFragment.context, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            val selectedTime = Calendar.getInstance()
            selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
            selectedTime.set(Calendar.MINUTE,minute)

            var time = DatesFormat.dateToTime(selectedTime.time).split(":")

            et_jumat_fromone.setText(time[0])
            et_jumat_fromtwo.setText(time[1])

            val timePickerEnd = TimePickerDialog(this@ScheduleManualFragment.context, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
                selectedTime.set(Calendar.MINUTE,minute)

                time = DatesFormat.dateToTime(selectedTime.time).split(":")

                et_jumat_toone.setText(time[0])
                et_jumat_totwo.setText(time[1])

                cb_jumat.isChecked = true
            },
                calendarEnd.get(Calendar.HOUR_OF_DAY), calendarEnd.get(Calendar.MINUTE),true)
            timePickerEnd.show()
        },
            calendarStart.get(Calendar.HOUR_OF_DAY), calendarStart.get(Calendar.MINUTE),true)
        timePickerStart.show()
    }

    private fun sabtuPickerDialog(){
        val calendarStart = Calendar.getInstance()
        val calendarEnd = Calendar.getInstance()

        val timePickerStart = TimePickerDialog(this@ScheduleManualFragment.context, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            val selectedTime = Calendar.getInstance()
            selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
            selectedTime.set(Calendar.MINUTE,minute)

            var time = DatesFormat.dateToTime(selectedTime.time).split(":")

            et_sabtu_fromone.setText(time[0])
            et_sabtu_fromtwo.setText(time[1])

            val timePickerEnd = TimePickerDialog(this@ScheduleManualFragment.context, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
                selectedTime.set(Calendar.MINUTE,minute)

                time = DatesFormat.dateToTime(selectedTime.time).split(":")

                et_sabtu_toone.setText(time[0])
                et_sabtu_totwo.setText(time[1])

                cb_sabtu.isChecked = true
            },
                calendarEnd.get(Calendar.HOUR_OF_DAY), calendarEnd.get(Calendar.MINUTE),true)
            timePickerEnd.show()
        },
            calendarStart.get(Calendar.HOUR_OF_DAY), calendarStart.get(Calendar.MINUTE),true)
        timePickerStart.show()
    }

    private fun mingguPickerDialog(){
        val calendarStart = Calendar.getInstance()
        val calendarEnd = Calendar.getInstance()

        val timePickerStart = TimePickerDialog(this@ScheduleManualFragment.context, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            val selectedTime = Calendar.getInstance()
            selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
            selectedTime.set(Calendar.MINUTE,minute)

            var time = DatesFormat.dateToTime(selectedTime.time).split(":")

            et_minggu_fromone.setText(time[0])
            et_minggu_fromtwo.setText(time[1])

            val timePickerEnd = TimePickerDialog(this@ScheduleManualFragment.context, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
                selectedTime.set(Calendar.MINUTE,minute)

                time = DatesFormat.dateToTime(selectedTime.time).split(":")

                et_minggu_toone.setText(time[0])
                et_minggu_totwo.setText(time[1])

                cb_minggu.isChecked = true

            },
                calendarEnd.get(Calendar.HOUR_OF_DAY), calendarEnd.get(Calendar.MINUTE),true)
            timePickerEnd.show()
        },
            calendarStart.get(Calendar.HOUR_OF_DAY), calendarStart.get(Calendar.MINUTE),true)
        timePickerStart.show()
    }

    private fun prepare(){
        FirebaseUtil.getCurrentUser {
            mUstad = it

            checkCb()
        }

        et_senin_fromone.isFocusable = false
        et_senin_fromtwo.isFocusable = false
        et_senin_toone.isFocusable = false
        et_senin_totwo.isFocusable = false

        et_selasa_fromone.isFocusable = false
        et_selasa_fromtwo.isFocusable = false
        et_selasa_toone.isFocusable = false
        et_selasa_totwo.isFocusable = false

        et_rabu_fromone.isFocusable = false
        et_rabu_fromtwo.isFocusable = false
        et_rabu_toone.isFocusable = false
        et_rabu_totwo.isFocusable = false

        et_kamis_fromone.isFocusable = false
        et_kamis_fromtwo.isFocusable = false
        et_kamis_toone.isFocusable = false
        et_kamis_totwo.isFocusable = false

        et_jumat_fromone.isFocusable = false
        et_jumat_fromtwo.isFocusable = false
        et_jumat_toone.isFocusable = false
        et_jumat_totwo.isFocusable = false

        et_sabtu_fromone.isFocusable = false
        et_sabtu_fromtwo.isFocusable = false
        et_sabtu_toone.isFocusable = false
        et_sabtu_totwo.isFocusable = false

        et_minggu_fromone.isFocusable = false
        et_minggu_fromtwo.isFocusable = false
        et_minggu_toone.isFocusable = false
        et_minggu_totwo.isFocusable = false

        et_senin_fromone.setOnClickListener { seninPickerDialog() }
        et_senin_fromtwo.setOnClickListener { seninPickerDialog() }
        et_senin_toone.setOnClickListener { seninPickerDialog() }
        et_senin_totwo.setOnClickListener { seninPickerDialog() }

        et_selasa_fromone.setOnClickListener { selasaPickerDialog() }
        et_selasa_fromtwo.setOnClickListener { selasaPickerDialog() }
        et_selasa_toone.setOnClickListener { selasaPickerDialog() }
        et_selasa_totwo.setOnClickListener { selasaPickerDialog() }

        et_rabu_fromone.setOnClickListener { rabuPickerDialog() }
        et_rabu_fromtwo.setOnClickListener { rabuPickerDialog() }
        et_rabu_toone.setOnClickListener { rabuPickerDialog() }
        et_rabu_totwo.setOnClickListener { rabuPickerDialog() }

        et_kamis_fromone.setOnClickListener { kamisPickerDialog() }
        et_kamis_fromtwo.setOnClickListener { kamisPickerDialog() }
        et_kamis_toone.setOnClickListener { kamisPickerDialog() }
        et_kamis_totwo.setOnClickListener { kamisPickerDialog() }

        et_jumat_fromone.setOnClickListener { jumatPickerDialog() }
        et_jumat_fromtwo.setOnClickListener { jumatPickerDialog() }
        et_jumat_toone.setOnClickListener { jumatPickerDialog() }
        et_jumat_totwo.setOnClickListener { jumatPickerDialog() }

        et_sabtu_fromone.setOnClickListener { sabtuPickerDialog() }
        et_sabtu_fromtwo.setOnClickListener { sabtuPickerDialog() }
        et_sabtu_toone.setOnClickListener { sabtuPickerDialog() }
        et_sabtu_totwo.setOnClickListener { sabtuPickerDialog() }

        et_minggu_fromone.setOnClickListener { mingguPickerDialog() }
        et_minggu_fromtwo.setOnClickListener { mingguPickerDialog() }
        et_minggu_toone.setOnClickListener { mingguPickerDialog() }
        et_minggu_totwo.setOnClickListener { mingguPickerDialog() }
    }

    private fun checkCb() {
        mUstad?.schedule!!.forEach {
            if (it.day == "Senin") {
                cb_senin.isChecked = true

                if (it.timeStart.isNotEmpty()){
                    val startTime = it.timeStart.split(":")
                    et_senin_fromone.setText(startTime[0])
                    et_senin_fromtwo.setText(startTime[1])
                }
                if (it.timeEnd.isNotEmpty()){
                    val endTime = it.timeEnd.split(":")
                    et_senin_toone.setText(endTime[0])
                    et_senin_totwo.setText(endTime[1])
                }

            }
            if (it.day == "Selasa") {
                cb_selasa.isChecked = true

                if (it.timeStart.isNotEmpty()){
                    val startTime = it.timeStart.split(":")
                    et_selasa_fromone.setText(startTime[0])
                    et_selasa_fromtwo.setText(startTime[1])
                }
                if (it.timeEnd.isNotEmpty()){
                    val endTime = it.timeEnd.split(":")
                    et_selasa_toone.setText(endTime[0])
                    et_selasa_totwo.setText(endTime[1])
                }
            }
            if (it.day == "Rabu") {
                cb_rabu.isChecked = true

                if (it.timeStart.isNotEmpty()){
                    val startTime = it.timeStart.split(":")
                    et_rabu_fromone.setText(startTime[0])
                    et_rabu_fromtwo.setText(startTime[1])
                }
                if (it.timeEnd.isNotEmpty()){
                    val endTime = it.timeEnd.split(":")
                    et_rabu_toone.setText(endTime[0])
                    et_rabu_totwo.setText(endTime[1])
                }
            }
            if (it.day == "Kamis") {
                cb_kamis.isChecked = true

                if (it.timeStart.isNotEmpty()){
                    val startTime = it.timeStart.split(":")
                    et_kamis_fromone.setText(startTime[0])
                    et_kamis_fromtwo.setText(startTime[1])
                }
                if (it.timeEnd.isNotEmpty()){
                    val endTime = it.timeEnd.split(":")
                    et_kamis_toone.setText(endTime[0])
                    et_kamis_totwo.setText(endTime[1])
                }
            }
            if (it.day == "Jumat") {
                cb_jumat.isChecked = true

                if (it.timeStart.isNotEmpty()){
                    val startTime = it.timeStart.split(":")
                    et_jumat_fromone.setText(startTime[0])
                    et_jumat_fromtwo.setText(startTime[1])
                }
                if (it.timeEnd.isNotEmpty()){
                    val endTime = it.timeEnd.split(":")
                    et_jumat_toone.setText(endTime[0])
                    et_jumat_totwo.setText(endTime[1])
                }
            }
            if (it.day == "Sabtu") {
                cb_sabtu.isChecked = true

                if (it.timeStart.isNotEmpty()){
                    val startTime = it.timeStart.split(":")
                    et_sabtu_fromone.setText(startTime[0])
                    et_sabtu_fromtwo.setText(startTime[1])
                }
                if (it.timeEnd.isNotEmpty()){
                    val endTime = it.timeEnd.split(":")
                    et_sabtu_toone.setText(endTime[0])
                    et_sabtu_totwo.setText(endTime[1])
                }
            }
            if (it.day == "Minggu") {
                cb_minggu.isChecked = true

                if (it.timeStart.isNotEmpty()){
                    val startTime = it.timeStart.split(":")
                    et_minggu_fromone.setText(startTime[0])
                    et_minggu_fromtwo.setText(startTime[1])
                }
                if (it.timeEnd.isNotEmpty()){
                    val endTime = it.timeEnd.split(":")
                    et_minggu_toone.setText(endTime[0])
                    et_minggu_totwo.setText(endTime[1])
                }
            }

        }
    }

}
