package com.example.finalskillsync.Room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface OppDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addOpp(opp:Opp )
    @Query(" Select* From opp order By oppId ASC")
    fun getOpp(): LiveData<List<Opp>>

}