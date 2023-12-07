package com.example.finalskillsync.Firebase.Models

import kotlin.random.Random

class Opportunitydata (
    var scholarshipId:Long= generateRandomScholarshipId(),
    var title: String? = null,
    var level: String? = null,
    var link: String? = null,
    var benefit: String? = null,
    var deadline: String?= null,
)
{
    companion object {
        fun generateRandomScholarshipId(): Long {
            return Random.nextLong(1000000L, 9999999L)
        }
    }
}