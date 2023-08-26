package com.verindrarizya.attendancefirebase.util

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

object CalendarUtils {

    private val localeIndo = Locale("id", "ID")

    private val hourFormat = SimpleDateFormat("hh:mm a", localeIndo)

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", localeIndo)

    val currentHour: String
        get() = hourFormat.format(Calendar.getInstance().time)

    val currentDate: String
        get() {
            return dateFormat.format(Calendar.getInstance().time)
        }

    val aWeekAgoDate: String
        get() {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DATE, -7)
            return dateFormat.format(calendar.time)
        }

    val aMonthAgoDate: String
        get() {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.MONTH, -1)
            return dateFormat.format(calendar.time)
        }

    val aYearAgoDate: String
        get() {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.YEAR, -1)
            return dateFormat.format(calendar.time)
        }

    fun getPreviousDayDate(inputDate: String): String {
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val date = LocalDate.parse(inputDate, dateFormatter)
        val previousDate = date.minusDays(1)
        return previousDate.format(dateFormatter)
    }
}