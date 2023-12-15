package com.example.finalskillsync.Room

import androidx.room.Entity


@Entity(tableName = "opp")
data class Opp(
    var oppId:Long?=null,
    var benefit: String? = null,
    var deadline: String? = null,
    var level: String? = null,
    var link: String? = null,
    var title: String?= null,
)