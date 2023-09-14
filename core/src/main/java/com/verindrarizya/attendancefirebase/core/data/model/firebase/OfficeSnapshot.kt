package com.verindrarizya.attendancefirebase.core.data.model.firebase

import com.google.firebase.database.IgnoreExtraProperties
import com.verindrarizya.attendancefirebase.core.entity.Office

@IgnoreExtraProperties
data class OfficeSnapshot(
    val id: Int? = null,
    val address: String? = null,
    val imageUrl: String? = null,
    val name: String? = null
)

fun OfficeSnapshot.toOffice() = Office(
    id = id ?: 0,
    address = address ?: "",
    imageUrl = imageUrl ?: "",
    name = name ?: ""
)