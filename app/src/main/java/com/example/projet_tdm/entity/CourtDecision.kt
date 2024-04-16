package com.example.projet_tdm.entity


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "court_decisions")
data class CourtDecision(
    @PrimaryKey val NumArret: String,
    val ChambreArret: String,
    val RefArret: String,
    val AnneeRef: String,
    val numRef: String,
    val numPageRef: String,
    val DateArret: String,
    val PartiesArret: String,
    val PrincipeArret: String,
    val DecisionArret: String,
    val DecisionOpArret: String,
    val CompositionArret: String
)