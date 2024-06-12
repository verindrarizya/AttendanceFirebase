package com.verindrarizya.attendancefirebase.ui.screens.dashboard.home

import com.verindrarizya.attendancefirebase.core.entity.Office
import com.verindrarizya.attendancefirebase.core.util.Resource

sealed interface HomeUiState {
    val isLoading: Boolean
    val isRefreshing: Boolean
    val isError: Boolean
    val isDialogCameraShow: Boolean
    val selectedOffice: Office?

    data class CheckInUiState(
        override val isLoading: Boolean,
        override val isRefreshing: Boolean,
        override val isError: Boolean,
        override val isDialogCameraShow: Boolean,
        val listOfOfficeResource: Resource<List<Office>> = Resource.Loading,
        override val selectedOffice: Office?
    ) : HomeUiState

    data class CheckOutUiState(
        override val isLoading: Boolean,
        override val isRefreshing: Boolean,
        override val isError: Boolean,
        override val isDialogCameraShow: Boolean,
        override val selectedOffice: Office
    ) : HomeUiState

}