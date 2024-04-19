package com.test.fininfocom.details.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "person_table")
data class Person(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var name: String? = null,
    var age: Int? = null,
    var city: String? = null,
    var profession: String? = null,
)