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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private data class HomeViewModelState(
    val todayAttendanceState: TodayAttendanceState,
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val isError: Boolean = false,
    val listOfOfficeResourceState: ResourceState<List<Office>> = ResourceState.Loading,
    val selectedOffice: Office? = null
) {

    fun toUiState(): HomeUiState {
        return if (todayAttendanceState is TodayAttendanceState.CheckedIn) {
            HomeUiState.CheckOutUiState(
                isLoading = isLoading,
                isRefreshing = isRefreshing,
                isError = isError,
                selectedOffice = selectedOffice!!
            )
        } else {
            HomeUiState.CheckInUiState(
                isLoading = isLoading,
                isRefreshing = isRefreshing,
                isError = isError,
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
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
            initialValue = _homeViewModelState.value.toUiState()
        )

    private val _messageFlow: MutableSharedFlow<String> = MutableSharedFlow()
    val messageFlow: SharedFlow<String> = _messageFlow.asSharedFlow()

    init {
        getTodayAttendanceStatus()
        getOffices()
    }

    private fun getTodayAttendanceStatus() {
        viewModelScope.launch {
            attendanceRepository.checkTodayAttendanceState().collect { resourceState ->
                when (resourceState) {
                    is ResourceState.Error -> {
                        _homeViewModelState.update {
                            it.copy(isLoading = false, isError = true)
                        }
                        _messageFlow.emit(resourceState.message)
                    }

                    ResourceState.Init -> { /* Do Nothing */
                    }

                    ResourceState.Loading -> {
                        _homeViewModelState.update { it.copy(isLoading = true, isError = false) }
                    }

                    is ResourceState.Success -> {
                        processTodayAttendanceState(resourceState.data)
                    }
                }
            }
        }
    }

    private fun processTodayAttendanceState(todayAttendanceState: TodayAttendanceState) {
        _homeViewModelState.update {
            it.copy(
                isLoading = false,
                todayAttendanceState = todayAttendanceState,
                selectedOffice = if (todayAttendanceState is TodayAttendanceState.CheckedIn)
                    todayAttendanceState.office
                else
                    null
            )
        }
    }

    private fun getOffices() {
        viewModelScope.launch {
            officeRepository.getOffices().collect { officesResource ->
                _homeViewModelState.update { it.copy(listOfOfficeResourceState = officesResource) }
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _homeViewModelState.update { it.copy(isRefreshing = true) }
            getTodayAttendanceStatus()
            getOffices()
            delay(200)
            _homeViewModelState.update { it.copy(isRefreshing = false) }
        }
    }

    fun selectOffice(office: Office) {
        _homeViewModelState.update { it.copy(selectedOffice = office) }
    }

    fun recordAttendance() {
        viewModelScope.launch {
            if (_homeViewModelState.value.selectedOffice == null) {
                _messageFlow.emit("Please Select an Office")
                return@launch
            }

            when (val attendanceState = _homeViewModelState.value.todayAttendanceState) {
                is TodayAttendanceState.CheckedIn -> {
                    recordNewAttendance(
                        attendanceState = AttendanceState.CheckOut,
                        office = attendanceState.office
                    )
                }

                TodayAttendanceState.CheckedOut -> {
                    _messageFlow.emit("Today Attendance Already Recorded")
                }

                TodayAttendanceState.NoRecord -> {
                    recordNewAttendance(
                        office = _homeViewModelState.value.selectedOffice!!,
                        attendanceState = AttendanceState.CheckIn
                    )
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
                    _homeViewModelState.update { it.copy(isLoading = false) }
                    _messageFlow.emit(resourceState.message)
                }

                ResourceState.Init -> { /* Do Nothing */
                }

                ResourceState.Loading -> {
                    _homeViewModelState.update { it.copy(isLoading = true) }
                }

                is ResourceState.Success -> {
                    _homeViewModelState.update { it.copy(isLoading = false) }
                    _messageFlow.emit(resourceState.data)
                }
            }
        }
    }
}