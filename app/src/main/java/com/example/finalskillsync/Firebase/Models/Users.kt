package com.example.finalskillsync.Firebase.Models

import kotlin.random.Random

data class Users(
    var userId: Long = generateRandomUserId(),
    var name: String? = null,
    var email: String? = null,
    var phoneNumebr: Int?= null,
    var password: String? = null,
) {
    companion object {
        private fun generateRandomUserId(): Long {
            return Random.nextLong(1000000L, 9999999L)
        }
    }
}