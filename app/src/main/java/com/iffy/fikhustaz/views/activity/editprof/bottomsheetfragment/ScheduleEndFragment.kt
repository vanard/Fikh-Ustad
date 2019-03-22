package com.iffy.fikhustaz.views.activity.editprof.bottomsheetfragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.iffy.fikhustaz.R
import kotlinx.android.synthetic.main.fragment_schedule_end.*
import org.jetbrains.anko.support.v4.toast
import java.util.*

class ScheduleEndFragment : Fragment() {

    private var day = Calendar.HOUR_OF_DAY
    private var minut = Calendar.MINUTE

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule_end, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        timePicker_end.setOnTimeChangedListener { view, hourOfDay, minute ->
            day = hourOfDay
            minut = minute
        }

        tv_save_end.setOnClickListener {
            toast("$day:$minut")
        }
    }

}
