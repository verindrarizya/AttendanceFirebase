package com.verindrarizya.attendancefirebase.core.data.repository.office

import com.verindrarizya.attendancefirebase.core.entity.Office
import kotlinx.coroutines.flow.Flow

interface OfficeRepository {

    fun getOffices(): Flow<com.verindrarizya.attendancefirebase.common.util.Resource<List<Office>>>

}