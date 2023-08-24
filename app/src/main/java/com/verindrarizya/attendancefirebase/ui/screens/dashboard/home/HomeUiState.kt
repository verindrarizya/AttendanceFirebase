package com.verindrarizya.attendancefirebase.ui.screens.dashboard.home

import com.verindrarizya.attendancefirebase.ui.model.Office
import com.verindrarizya.attendancefirebase.util.ResourceState

sealed interface HomeUiState {
    val isLoading: Boolean
    val isRefreshing: Boolean
    val isError: Boolean

    data class CheckInUiState(
        override val isLoading: Boolean = false,
        override val isRefreshing: Boolean = false,
        override val isError: Boolean = false,
        val listOfOfficeResourceState: ResourceState<List<Office>> = ResourceState.Loading,
        val selectedOffice: Office? = null
    ) : HomeUiState

    data class CheckOutUiState(
        override val isLoading: Boolean = false,
        override val isRefreshing: Boolean = false,
        override val isError: Boolean = false,
        val selectedOffice: Office
    ) : HomeUiState

}