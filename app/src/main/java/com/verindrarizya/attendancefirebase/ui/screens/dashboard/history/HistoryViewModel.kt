package com.verindrarizya.attendancefirebase.ui.screens.dashboard.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.verindrarizya.attendancefirebase.data.repository.AttendanceRepository
import com.verindrarizya.attendancefirebase.util.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val attendanceRepository: AttendanceRepository
) : ViewModel() {

    private val _selectedHistoryDateFilter: MutableStateFlow<HistoryDateFilter> =
        MutableStateFlow(HistoryDateFilter.Day())
    val selectedHistoryDateFilter = _selectedHistoryDateFilter.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val attendanceRecordResourceState = _selectedHistoryDateFilter.flatMapLatest {
        attendanceRepository.getAttendanceRecords(
            it.startDate,
            it.endDate
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ResourceState.Init
    )

    fun selectHistoryDateFilter(historyDateFilter: HistoryDateFilter) {
        _selectedHistoryDateFilter.value = historyDateFilter
    }
}