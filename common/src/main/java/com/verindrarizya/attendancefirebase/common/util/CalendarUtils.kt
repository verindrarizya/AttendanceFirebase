package com.verindrarizya.attendancefirebase.common.util

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
            return dateFormat.format(Calendar.getInstance(localeIndo).time)
        }

    val beginningWeekDate: String
        get() {
            val calendar = Calendar.getInstance(localeIndo)
            calendar.firstDayOfWeek = Calendar.MONDAY
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
            return dateFormat.format(calendar.time)
        }

    val beginningMonthDate: String
        get() {
            val calendar = Calendar.getInstance(localeIndo)
            calendar.set(Calendar.DAY_OF_MONTH, 1)
            return dateFormat.format(calendar.time)
        }

    val beginningYearDate: String
        get() {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.MONTH, Calendar.JANUARY)
            calendar.set(Calendar.DAY_OF_MONTH, 1)
            return dateFormat.format(calendar.time)
        }

    fun getPreviousDayDate(inputDate: String): String {
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val date = LocalDate.parse(inputDate, dateFormatter)
        val previousDate = date.minusDays(1)
        return previousDate.format(dateFormatter)
    }
}