package com.example.projet_tdm.database


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.lifecycle.LiveData



import com.example.projet_tdm.entity.CourtDecision

@Dao
interface CourtDecisionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCourtDecision(vararg courtDecisions: CourtDecision)

    @Query("SELECT * FROM court_decisions ORDER BY DateArret DESC")
    fun getAllCourtDecisions(): LiveData<List<CourtDecision>>

    @Query("SELECT * FROM court_decisions WHERE NumArret = :numArret")
    suspend fun getCourtDecisionByNumArret(numArret: String?): CourtDecision?

    @Query("SELECT * FROM court_decisions WHERE (ChambreArret LIKE '%' || :chambreArret || '%')")
    suspend fun getCourtDecisionsByChambreArret(chambreArret: String?): List<CourtDecision>

    @Query("SELECT * FROM court_decisions WHERE (AnneeRef LIKE '%' || :anneeRef || '%')")
    suspend fun getCourtDecisionsByAnneeRef(anneeRef: String?): List<CourtDecision>

    @Query("SELECT * FROM court_decisions WHERE (RefArret LIKE '%' || :refArret || '%')")
    suspend fun getCourtDecisionsByRefArret(refArret: String?): List<CourtDecision>

    @Query("SELECT * FROM court_decisions WHERE " +
            "(:chambreArret IS NULL OR ChambreArret LIKE '%' || :chambreArret || '%') AND " +
            "(:anneeRef IS NULL OR AnneeRef LIKE '%' || :anneeRef || '%') AND " +
            "(:refArret IS NULL OR RefArret LIKE '%' || :refArret || '%')")
    suspend fun getCourtDecisionsWithFilter(
        chambreArret: String?,
        anneeRef: String?,
        refArret: String?
    ): List<CourtDecision>

//    @Query("UPDATE court_decisions SET isLiked = :isLiked WHERE NumArret = :numArret")
//    suspend fun updateLikedStatus(numArret: String, isLiked: Boolean)
//
//    @Query("SELECT * FROM court_decisions WHERE isLiked = 1")
//    fun getLikedCourtDecisions(): LiveData<List<CourtDecision>>

    @Query("DELETE FROM court_decisions")
    fun clearAll()
}