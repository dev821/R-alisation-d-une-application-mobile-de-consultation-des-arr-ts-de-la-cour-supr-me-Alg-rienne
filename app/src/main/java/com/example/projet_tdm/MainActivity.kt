package com.example.projet_tdm

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnBrowseDataLoader = findViewById<Button>(R.id.btnBrowseDataLoader)
        val btnBrowseCourtDecision = findViewById<Button>(R.id.btnBrowseCourtDecision)

        btnBrowseDataLoader.setOnClickListener {
            startActivity(Intent(this, DataLoaderActivity::class.java))
        }

        btnBrowseCourtDecision.setOnClickListener {
            startActivity(Intent(this, CourtDecisionListActivity::class.java))
        }
    }
}