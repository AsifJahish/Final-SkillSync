package com.example.finalskillsync.Firebase.Models

import kotlin.random.Random

data class Opportunity (
    var oppId:Long= generateRandomOppId(),
    var benefit: String? = null,
    var deadline: String? = null,
    var level: String? = null,
    var link: String? = null,
    var title: String?= null,
)
{
    companion object {
        fun generateRandomOppId(): Long {
            return Random.nextLong(1000000L, 9999999L)
        }
    }
}
