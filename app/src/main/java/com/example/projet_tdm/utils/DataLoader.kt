package com.example.projet_tdm.utils

import android.content.Context
import com.example.projet_tdm.database.AppDatabase
import com.example.projet_tdm.entity.CourtDecision
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object DataLoader {
    suspend fun loadDataFromJson(context: Context) {
        try {
            // Read JSON file from assets
            val jsonString = context.assets.open("arrets_cour_suppreme_utf8.json").bufferedReader().use { it.readText() }

            // Parse JSON data into an array of CourtDecision
            val courtDecisions = Gson().fromJson(jsonString, Array<CourtDecision>::class.java)

            // Initialize Room database and insert data in a transaction
            withContext(Dispatchers.IO) {
                val database = AppDatabase.getInstance(context)
                database.courtDecisionDao().insertCourtDecision(*courtDecisions)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun clearDatabase(context: Context) {
        try {
            // Clear all data from Room database
            withContext(Dispatchers.IO) {
                val database = AppDatabase.getInstance(context)
                database.courtDecisionDao().clearAll()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
