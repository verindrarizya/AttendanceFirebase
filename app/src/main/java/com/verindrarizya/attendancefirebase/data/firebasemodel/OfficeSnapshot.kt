package com.verindrarizya.attendancefirebase.data.firebasemodel

import com.google.firebase.database.IgnoreExtraProperties
import com.verindrarizya.attendancefirebase.ui.model.Office

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