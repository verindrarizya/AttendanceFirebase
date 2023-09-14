package com.verindrarizya.attendancefirebase.util

import org.junit.Test

class CalendarUtilsTest {

    @Test
    fun currentHourTest() {
        val currentHour = com.verindrarizya.attendancefirebase.common.util.CalendarUtils.currentHour
        println(currentHour)
    }

    @Test
    fun dateTest() {
        println("Current date: ${com.verindrarizya.attendancefirebase.common.util.CalendarUtils.currentDate}")
        println("A week ago date: ${com.verindrarizya.attendancefirebase.common.util.CalendarUtils.beginningWeekDate}")
        println("A month ago date: ${com.verindrarizya.attendancefirebase.common.util.CalendarUtils.beginningMonthDate}")
        println("A year ago date: ${com.verindrarizya.attendancefirebase.common.util.CalendarUtils.beginningYearDate}")
    }
}