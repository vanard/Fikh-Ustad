package com.iffy.fikhustaz.util

import org.junit.Assert
import org.junit.Test

class DateFormatTest {
    @Test
    fun dateStringFormatTest(){
        Assert.assertEquals(
            "05 November 2018",
            DatesFormat.reformatStringDate("2018-11-05", "yyyy-MM-dd", DatesFormat.DATE_FORMAT_OUTPUT)
        )

        Assert.assertEquals(
            "Mon",
            DatesFormat.reformatStringDate("2018-11-05", "yyyy-MM-dd", DatesFormat.DATE)
        )
    }
}