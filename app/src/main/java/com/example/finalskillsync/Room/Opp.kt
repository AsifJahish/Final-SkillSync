package com.example.finalskillsync.Room

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "opp")
data class Opp(
    @PrimaryKey
    var oppId:Long?=null,
    var benefit: String? = null,
    var deadline: String? = null,
    var level: String? = null,
    var link: String? = null,
    var title: String?= null,
    var userEmail: String?= null,
)