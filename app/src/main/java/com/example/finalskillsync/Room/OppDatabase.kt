package com.example.finalskillsync.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Opp::class], version = 1, exportSchema = false)
abstract class OppDatabase: RoomDatabase() {

    abstract fun oppDao(): OppDao

    companion object{
        @Volatile
        private var INSTANCE: OppDatabase?= null

        fun getDatabase(context: Context): OppDatabase{
            val tempInstance= INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
           synchronized(this){
               val instance= Room.databaseBuilder(
                   context.applicationContext,
                   OppDatabase::class.java,
                   "opp"
               ).build()
               INSTANCE= instance
               return instance
           }
        }
    }

}