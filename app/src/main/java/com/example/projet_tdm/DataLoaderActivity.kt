package com.example.projet_tdm

import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projet_tdm.utils.DataLoader
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DataLoaderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_loader)

        val btnLoadData = findViewById<MaterialButton>(R.id.btnLoadData)
        val btnClearDatabase = findViewById<MaterialButton>(R.id.btnClearDatabase)
        val btnBack = findViewById<ImageButton>(R.id.btnBack)

        btnLoadData.setOnClickListener {
            loadData()
        }

        btnClearDatabase.setOnClickListener {
            clearDatabase()
        }

        btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun loadData() {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                DataLoader.loadDataFromJson(applicationContext)
                showToast("Data loaded successfully")
            } catch (e: Exception) {
                showToast("Error loading data: ${e.message}")
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun clearDatabase() {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                DataLoader.clearDatabase(applicationContext)
                showToast("Database cleared successfully")
            } catch (e: Exception) {
                showToast("Error clearing database: ${e.message}")
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}
