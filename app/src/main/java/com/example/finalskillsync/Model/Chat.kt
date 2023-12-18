package com.example.finalskillsync.Model

import java.util.Calendar
import kotlin.random.Random


data class Chat(
    var chatId: Long?= generateRandomShareId(),
    var userId: Long? = null,
    var timestamp: Long = getCurrentTimestamp(),
    var chat: String? = null,
    var userName: String? = null,
    var oppTitle:String?= null,

    )
{
    companion object {
        fun generateRandomShareId(): Long {
            return Random.nextLong(1000000L, 9999999L)
        }

        fun getCurrentTimestamp(): Long {
            return Calendar.getInstance().timeInMillis
        }
    }
}