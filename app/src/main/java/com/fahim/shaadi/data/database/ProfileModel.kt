package com.fahim.shaadi.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profiles")
data class ProfileModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var name: String? = null,
    var age: Int? = null,
    var city: String? = null,
    var gender: String? = null,
    var email: String? = null,
    var picture: String? = null,
    var phone: String? = null,
    var isAccepted: Int = -1
)