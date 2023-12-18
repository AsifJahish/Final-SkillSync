package com.example.finalskillsync.Repository

import androidx.lifecycle.LiveData
import com.example.finalskillsync.Model.Opp
import com.example.finalskillsync.Dao.OppDao
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