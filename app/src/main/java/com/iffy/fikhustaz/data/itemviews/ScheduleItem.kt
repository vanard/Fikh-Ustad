package com.iffy.fikhustaz.data.itemviews

import com.iffy.fikhustaz.R
import com.iffy.fikhustaz.data.model.profile.ItSchedule
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_schedule.*

class ScheduleItem (val item: ItSchedule) : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.tv_day_schedule.text = item.day
        viewHolder.tv_time_schedule.text = "${item.timeStart} - ${item.timeEnd}"
    }

    override fun getLayout() = R.layout.item_schedule

}