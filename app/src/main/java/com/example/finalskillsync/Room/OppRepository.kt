package com.example.finalskillsync.Room

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OppRepository(private val userDao: OppDao) {

    val readAllData: LiveData<List<Opp>> = userDao.getOpp()

    suspend fun addOp(opp: Opp) {
        // Use withContext to switch to IO dispatcher for database operations
        withContext(Dispatchers.IO) {
            userDao.addOpp(opp)
        }
    }


}