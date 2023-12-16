package com.example.finalskillsync.Room

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OppRepository(private val oppDoa: OppDao) {

    val readAllData: LiveData<List<Opp>> = oppDoa.getOpp()

    suspend fun addOp(opp: Opp) {
        // Use withContext to switch to IO dispatcher for database operations
        withContext(Dispatchers.IO) {
            oppDoa.addOpp(opp)
        }
    }
}