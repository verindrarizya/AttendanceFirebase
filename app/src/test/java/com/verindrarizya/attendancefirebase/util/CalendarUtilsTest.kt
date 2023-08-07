package com.verindrarizya.attendancefirebase.util

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
        println("A week ago date: ${CalendarUtils.aWeekAgoDate}")
        println("A month ago date: ${CalendarUtils.aMonthAgoDate}")
        println("A year ago date: ${CalendarUtils.aYearAgoDate}")
    }

}