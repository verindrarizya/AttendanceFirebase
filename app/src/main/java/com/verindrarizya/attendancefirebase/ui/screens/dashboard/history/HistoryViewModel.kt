package com.verindrarizya.attendancefirebase.ui.screens.dashboard.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.verindrarizya.attendancefirebase.data.repository.AttendanceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val attendanceRepository: AttendanceRepository
) : ViewModel() {

    private val _selectedHistoryDateFilter: MutableStateFlow<HistoryDateFilter> =
        MutableStateFlow(HistoryDateFilter.Day())
    val selectedHistoryDateFilter = _selectedHistoryDateFilter.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val attendanceRecordPaging = _selectedHistoryDateFilter.flatMapLatest {
        Pager(
            PagingConfig(pageSize = 10)
        ) {
            attendanceRepository.getAttendanceRecordsPagingSource(
                startDate = it.startDate,
                endDate = it.endDate
            )
        }.flow
    }.cachedIn(viewModelScope)

    fun selectHistoryDateFilter(historyDateFilter: HistoryDateFilter) {
        _selectedHistoryDateFilter.value = historyDateFilter
    }
}