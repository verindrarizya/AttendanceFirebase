package com.verindrarizya.attendancefirebase.ui.screens.dashboard.home

import com.verindrarizya.attendancefirebase.core.entity.Office
import com.verindrarizya.attendancefirebase.core.util.Resource

sealed interface HomeUiState {
    val isLoading: Boolean
    val isRefreshing: Boolean
    val isError: Boolean

    data class CheckInUiState(
        override val isLoading: Boolean = false,
        override val isRefreshing: Boolean = false,
        override val isError: Boolean = false,
        val listOfOfficeResource: Resource<List<Office>> = Resource.Loading,
        val selectedOffice: Office? = null
    ) : HomeUiState

    data class CheckOutUiState(
        override val isLoading: Boolean = false,
        override val isRefreshing: Boolean = false,
        override val isError: Boolean = false,
        val selectedOffice: Office
    ) : HomeUiState

}