package com.verindrarizya.attendancefirebase.core.data.repository.office

import com.verindrarizya.attendancefirebase.core.entity.Office
import com.verindrarizya.attendancefirebase.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface OfficeRepository {

    fun getOffices(): Flow<Resource<List<Office>>>

}