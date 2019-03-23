package com.iffy.fikhustaz.util

import java.text.SimpleDateFormat
import java.util.*

class DatesFormat {

    companion object {

        const val DATE_FORMAT = "d/M/yyyy"
        const val DATE_FORMAT_OUTPUT = "dd MMMM yyyy"
        const val DATE_FORMAT_TIME_LOCAL = "HH:mm"
        const val DATE_FORMAT_TIME = "HH:mm:ssXXX"
        const val DATE = "EEE"

        private fun stringToDate(strDate: String, formatInput: String): Date {
            return if (strDate.isNullOrEmpty()) {
                Calendar.getInstance().time
            } else {
                SimpleDateFormat(formatInput, Locale.getDefault()).parse(strDate)
            }
        }

        private fun dateToString(date: Date, formatOutput: String): String {
            return SimpleDateFormat(formatOutput, Locale.getDefault()).format(date)
        }

        fun reformatStringDate(stringDate: String, formatInput: String, formatOutput: String): String {
            return dateToString(
                stringToDate(
                    stringDate,
                    formatInput
                ), formatOutput
            )
        }

        fun secondToDate(long: Long): String{
            val formatter = SimpleDateFormat(DATE, Locale.getDefault())
            return formatter.format(Date(long * 1000L))
        }
    }

}