package com.example.projet_tdm

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projet_tdm.database.AppDatabase
import com.example.projet_tdm.database.CourtDecisionDao
import com.example.projet_tdm.entity.CourtDecision
import com.example.projet_tdm.entity.CourtDecisionFilter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CourtDecisionListActivity : AppCompatActivity() {

    private lateinit var courtDecisionAdapter: CourtDecisionAdapter
    private lateinit var courtDecisionDao: CourtDecisionDao
    private var currentFilter: CourtDecisionFilter? = null
    private lateinit var chambreArretSpinner: Spinner
    private lateinit var anneeRefSpinner: Spinner
    private lateinit var refArretSpinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_court_decision_list)

        // Initialize Room database
        val appDatabase = AppDatabase.getInstance(this)
        courtDecisionDao = appDatabase.courtDecisionDao()
        courtDecisionAdapter = CourtDecisionAdapter()

        // Initialize Spinners
        chambreArretSpinner = findViewById(R.id.spinnerChambreArret)
        anneeRefSpinner = findViewById(R.id.spinnerAnneeRef)
        refArretSpinner = findViewById(R.id.spinnerRefArret)

        // Set up adapters
        val chambreArretAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.chambre_arret_labels,
            android.R.layout.simple_spinner_item
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        chambreArretSpinner.adapter = chambreArretAdapter

        val anneeRefAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.annee_ref_labels,
            android.R.layout.simple_spinner_item
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        anneeRefSpinner.adapter = anneeRefAdapter

        val refArretAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.ref_arret_labels,
            android.R.layout.simple_spinner_item
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        refArretSpinner.adapter = refArretAdapter

        // Set up filter button click listener
        val filterButton: Button = findViewById(R.id.btnFilter)
        filterButton.setOnClickListener {
            applyFilter()
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewCourtDecisions)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = courtDecisionAdapter

        observeCourtDecisions(courtDecisionDao)
    }

    private fun observeCourtDecisions(courtDecisionDao: CourtDecisionDao) {
        courtDecisionDao.getAllCourtDecisions().observe(this, Observer {
            courtDecisionAdapter.submitList(it)
        })
    }

    private fun applyFilter() {
        // Get selected values from Spinners
        val chambreArret = chambreArretSpinner.selectedItem as? String
        val anneeRef = anneeRefSpinner.selectedItem as? String
        val refArret = refArretSpinner.selectedItem as? String

        // Apply the filter
        currentFilter = CourtDecisionFilter(
            chambreArret = chambreArret,
            anneeRef = anneeRef,
            refArret = refArret
        )

        // Update the RecyclerView with filtered data
        updateRecyclerView()
    }

    // CourtDecisionListActivity.kt
    private fun updateRecyclerView() {
        GlobalScope.launch(Dispatchers.IO) {
            // Fetch CourtDecisions based on the current filter
            val filteredCourtDecisions = courtDecisionDao.getCourtDecisionsWithFilter(
                currentFilter?.chambreArret,
                currentFilter?.anneeRef,
                currentFilter?.refArret
            )

            // Update the UI with the filtered data (consider using Main dispatcher)
            launch(Dispatchers.Main) {
                courtDecisionAdapter.submitList(filteredCourtDecisions)
            }
        }
    }
}

