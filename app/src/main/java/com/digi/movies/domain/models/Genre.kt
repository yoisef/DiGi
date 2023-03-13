package com.digi.movies.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "genre")
data class Genre (
    val id: Int?=null,
    @PrimaryKey(autoGenerate = true)
    var ig:Long = 0,
    val name: String?=null
) : java.io.Serializable