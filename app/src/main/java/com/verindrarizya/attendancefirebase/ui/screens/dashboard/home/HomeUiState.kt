package com.verindrarizya.attendancefirebase.ui.screens.dashboard.home

import com.verindrarizya.attendancefirebase.ui.model.Office
import com.verindrarizya.attendancefirebase.util.ResourceState

sealed interface HomeUiState {
    val isLoading: Boolean

    data class CheckInUiState(
        override val isLoading: Boolean = true,
        val listOfOfficeResourceState: ResourceState<List<Office>> = ResourceState.Loading,
        val selectedOffice: Office? = null
    ) : HomeUiState

    data class CheckOutUiState(
        override val isLoading: Boolean = true,
        val selectedOffice: Office
    ) : HomeUiState

}