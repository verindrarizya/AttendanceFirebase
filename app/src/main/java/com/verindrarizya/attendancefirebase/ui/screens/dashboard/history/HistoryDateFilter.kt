package com.verindrarizya.attendancefirebase.ui.screens.dashboard.history

import com.verindrarizya.attendancefirebase.R

sealed class HistoryDateFilter {
    abstract val nameStrResource: Int
    abstract val startDate: String
    abstract val endDate: String


    data class Day(
        override val nameStrResource: Int = R.string.day,
        override val startDate: String = com.verindrarizya.attendancefirebase.common.util.CalendarUtils.currentDate,
        override val endDate: String = com.verindrarizya.attendancefirebase.common.util.CalendarUtils.currentDate
    ) : HistoryDateFilter()

    data class Week(
        override val nameStrResource: Int = R.string.week,
        override val startDate: String = com.verindrarizya.attendancefirebase.common.util.CalendarUtils.currentDate,
        override val endDate: String = com.verindrarizya.attendancefirebase.common.util.CalendarUtils.beginningWeekDate
    ) : HistoryDateFilter()

    data class Month(
        override val nameStrResource: Int = R.string.month,
        override val startDate: String = com.verindrarizya.attendancefirebase.common.util.CalendarUtils.currentDate,
        override val endDate: String = com.verindrarizya.attendancefirebase.common.util.CalendarUtils.beginningMonthDate
    ) : HistoryDateFilter()

    data class Year(
        override val nameStrResource: Int = R.string.year,
        override val startDate: String = com.verindrarizya.attendancefirebase.common.util.CalendarUtils.currentDate,
        override val endDate: String = com.verindrarizya.attendancefirebase.common.util.CalendarUtils.beginningYearDate
    ) : HistoryDateFilter()
}