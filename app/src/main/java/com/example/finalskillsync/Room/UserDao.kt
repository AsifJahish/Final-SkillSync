package com.example.finalskillsync.Room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface UserDao {
@Insert(onConflict = OnConflictStrategy.IGNORE)
suspend fun addOpp(opp:Opp )
@Query("Select* From opp orderBy oppId")
fun getOpp(): LiveData<List<Opp>>

}