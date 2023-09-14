package com.verindrarizya.attendancefirebase.data.repository.office

import com.verindrarizya.attendancefirebase.common.util.Resource
import com.verindrarizya.attendancefirebase.ui.model.Office
import kotlinx.coroutines.flow.Flow

interface OfficeRepository {

    fun getOffices(): Flow<Resource<List<Office>>>

}