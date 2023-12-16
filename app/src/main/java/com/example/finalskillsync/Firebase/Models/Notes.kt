package com.example.finalskillsync.Firebase.Models

import kotlin.random.Random

data class Notes (
    var noteId: Long = Notes.generateRandomnoteId(),
    var title: String? = null,
    var detial: String? = null,
    var userEmail: String?= null,
) {
    companion object {
        private fun generateRandomnoteId(): Long {
            return Random.nextLong(1000000L, 9999999L)
        }
    }
}