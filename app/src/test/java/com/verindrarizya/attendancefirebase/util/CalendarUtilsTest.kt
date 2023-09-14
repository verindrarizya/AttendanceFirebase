package com.verindrarizya.attendancefirebase.util

import com.verindrarizya.attendancefirebase.common.util.CalendarUtils
import org.junit.Test

class CalendarUtilsTest {

    @Test
    fun currentHourTest() {
        val currentHour = CalendarUtils.currentHour
        println(currentHour)
    }

    @Test
    fun dateTest() {
        println("Current date: ${CalendarUtils.currentDate}")
        println("A week ago date: ${CalendarUtils.beginningWeekDate}")
        println("A month ago date: ${CalendarUtils.beginningMonthDate}")
        println("A year ago date: ${CalendarUtils.beginningYearDate}")
    }
}