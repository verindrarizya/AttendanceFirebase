package com.verindrarizya.attendancefirebase.ui.screens.dashboard.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.verindrarizya.attendancefirebase.data.repository.AttendanceRepository
import com.verindrarizya.attendancefirebase.data.repository.OfficeRepository
import com.verindrarizya.attendancefirebase.ui.model.Office
import com.verindrarizya.attendancefirebase.util.AttendanceState
import com.verindrarizya.attendancefirebase.util.ResourceState
import com.verindrarizya.attendancefirebase.util.TodayAttendanceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private data class HomeViewModelState(
    val todayAttendanceState: TodayAttendanceState,
    val isLoading: Boolean = true,
    val listOfOfficeResourceState: ResourceState<List<Office>> = ResourceState.Loading,
    val selectedOffice: Office? = null
) {

    fun toUiState(): HomeUiState {
        return if (todayAttendanceState == TodayAttendanceState.CheckedIn) {
            HomeUiState.CheckOutUiState(
                isLoading = isLoading,
                selectedOffice = selectedOffice!!
            )
        } else {
            HomeUiState.CheckInUiState(
                isLoading = isLoading,
                listOfOfficeResourceState = listOfOfficeResourceState,
                selectedOffice = selectedOffice
            )
        }
    }

}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val officeRepository: OfficeRepository,
    private val attendanceRepository: AttendanceRepository
) : ViewModel() {

    private val _homeViewModelState = MutableStateFlow(
        HomeViewModelState(
            todayAttendanceState = TodayAttendanceState.NoRecord,
            isLoading = true
        )
    )

    val uiState: StateFlow<HomeUiState> = _homeViewModelState
        .map { it.toUiState() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = _homeViewModelState.value.toUiState()
        )

    private val _attendanceRecordFlow: MutableSharedFlow<String?> = MutableSharedFlow()
    val attendanceRecordFlow: SharedFlow<String?> = _attendanceRecordFlow.asSharedFlow()

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            attendanceRepository.checkTodayAttendanceState().collect {
                when (it) {
                    is ResourceState.Loading -> {
                        _homeViewModelState.update { state ->
                            state.copy(
                                isLoading = true
                            )
                        }
                    }

                    is ResourceState.Success -> {
                        processTodayAttendanceState(it)
                    }

                    is ResourceState.Error -> {
                        _homeViewModelState.update { it.copy(isLoading = false) }
                        _attendanceRecordFlow.emit(it.message)
                    }

                    is ResourceState.Init -> { /* Do Nothing */
                    }
                }
            }
        }
    }

    private suspend fun processTodayAttendanceState(state: ResourceState.Success<TodayAttendanceState>) {
        _homeViewModelState.update {
            it.copy(
                isLoading = false,
                todayAttendanceState = state.data,
            )
        }

        if (_homeViewModelState.value.selectedOffice == null && state.data == TodayAttendanceState.CheckedIn) {
            _homeViewModelState.update {
                it.copy(selectedOffice = attendanceRepository.officeLocation.first())
            }
        }

        if (state.data != TodayAttendanceState.CheckedIn) {
            getOffices()
        }
    }

    private suspend fun getOffices() {
        officeRepository.getOffices().collect {
            _homeViewModelState.update { state ->
                state.copy(
                    listOfOfficeResourceState = it
                )
            }
        }
    }

    fun selectOffice(office: Office) {
        _homeViewModelState.update { it.copy(selectedOffice = office) }
    }

    fun recordAttendance() {
        viewModelScope.launch {
            if (_homeViewModelState.value.selectedOffice == null) {
                _attendanceRecordFlow.emit("Select an Office")
                this.cancel()
                return@launch
            }

            when (_homeViewModelState.value.todayAttendanceState) {
                TodayAttendanceState.NoRecord -> {
                    recordNewAttendance(
                        _homeViewModelState.value.selectedOffice!!,
                        AttendanceState.CheckIn
                    )
                }

                TodayAttendanceState.CheckedIn -> {
                    recordNewAttendance(
                        _homeViewModelState.value.selectedOffice!!,
                        AttendanceState.CheckOut
                    )
                }

                TodayAttendanceState.CheckedOut -> {
                    _attendanceRecordFlow.emit("Today Attendance Already Recorded")
                }
            }
        }
    }

    private suspend fun recordNewAttendance(
        office: Office,
        attendanceState: AttendanceState
    ) {
        attendanceRepository.recordAttendance(office, attendanceState).collect { resourceState ->
            when (resourceState) {
                is ResourceState.Error -> {
                    _attendanceRecordFlow.emit(resourceState.message)
                    _homeViewModelState.update { it.copy(isLoading = false) }
                }

                ResourceState.Init -> { /* Do Nothing */
                }

                ResourceState.Loading -> {
                    _homeViewModelState.update { it.copy(isLoading = true) }
                }

                is ResourceState.Success -> {
                    refresh()
                    _homeViewModelState.update { it.copy(isLoading = false) }
                    _attendanceRecordFlow.emit(resourceState.data)
                }
            }
        }
    }
}