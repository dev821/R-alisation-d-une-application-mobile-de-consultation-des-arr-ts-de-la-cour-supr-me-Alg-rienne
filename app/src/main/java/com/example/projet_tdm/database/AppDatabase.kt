// AppDatabase.kt
package com.example.projet_tdm.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.projet_tdm.database.CourtDecisionDao
import com.example.projet_tdm.entity.CourtDecision

@Database(entities = [CourtDecision::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun courtDecisionDao(): CourtDecisionDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "court_decisions"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
