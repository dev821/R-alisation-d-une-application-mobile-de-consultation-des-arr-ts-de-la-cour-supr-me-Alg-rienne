// src/main/java/com/example/projet_tdm/CourtDecisionDetailActivity.kt
package com.example.projet_tdm

import android.os.Bundle
import android.text.Html
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.projet_tdm.database.AppDatabase
import com.example.projet_tdm.database.CourtDecisionDao
import com.example.projet_tdm.entity.CourtDecision
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CourtDecisionDetailActivity : AppCompatActivity() {

    private lateinit var courtDecisionDao: CourtDecisionDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_court_decision_detail)

        courtDecisionDao = AppDatabase.getInstance(this).courtDecisionDao()

        val numArret = intent.getStringExtra(COURT_DECISION_NUM_KEY)

        GlobalScope.launch(Dispatchers.IO) {
            val courtDecision = courtDecisionDao.getCourtDecisionByNumArret(numArret)
            launch(Dispatchers.Main) {
                courtDecision?.let {
                    val boldUnderlinedText = "<b><u>%s</u></b>"

                    findViewById<TextView>(R.id.tvNumArret).text = Html.fromHtml("<b><u>القرار رقم:</u></b> ${it.NumArret}\n\n", Html.FROM_HTML_MODE_LEGACY)
                    findViewById<TextView>(R.id.tvChambreArret).text = Html.fromHtml("<b><u>الصادر عن:</u></b> ${it.ChambreArret}\n\n", Html.FROM_HTML_MODE_LEGACY)
                    findViewById<TextView>(R.id.tvDateArret).text = Html.fromHtml("<b><u>بتاريخ:</u></b> ${it.DateArret}\n\n", Html.FROM_HTML_MODE_LEGACY)
                    findViewById<TextView>(R.id.tvRefArret).text = Html.fromHtml("<b><u>الرقم المرجعي:</u></b> ${it.RefArret}\n\n", Html.FROM_HTML_MODE_LEGACY)
                    findViewById<TextView>(R.id.tvAnneeRef).text = Html.fromHtml("<b><u>السنة المرجعية:</u></b> ${it.AnneeRef}\n\n", Html.FROM_HTML_MODE_LEGACY)
                    findViewById<TextView>(R.id.tvNumRef).text = Html.fromHtml("<b><u>رقم الاحالة:</u></b> ${it.numRef}\n\n", Html.FROM_HTML_MODE_LEGACY)
                    findViewById<TextView>(R.id.tvNumPageRef).text = Html.fromHtml("<b><u>رقم الصفحة:</u></b> ${it.numPageRef}\n\n", Html.FROM_HTML_MODE_LEGACY)
                    findViewById<TextView>(R.id.tvPartiesArret).text = Html.fromHtml("<b><u>الأطراف:</u></b> ${it.PartiesArret}\n\n", Html.FROM_HTML_MODE_LEGACY)
                    findViewById<TextView>(R.id.tvPrincipeArret).text = Html.fromHtml("<b><u>المبدأ:</u></b> ${it.PrincipeArret}\n\n", Html.FROM_HTML_MODE_LEGACY)
                    findViewById<TextView>(R.id.tvDecisionArret).text = Html.fromHtml("<b><u>القرار:</u></b> ${it.DecisionArret}\n\n", Html.FROM_HTML_MODE_LEGACY)
                    findViewById<TextView>(R.id.tvDecisionOpArret).text = Html.fromHtml("<b><u>قرار الفتح:</u></b> ${it.DecisionOpArret}\n\n", Html.FROM_HTML_MODE_LEGACY)
                    findViewById<TextView>(R.id.tvCompositionArret).text = Html.fromHtml("<b><u>تكوين اللجنة:</u></b> ${it.CompositionArret}\n\n", Html.FROM_HTML_MODE_LEGACY)
            }
            }
        }
    }
    companion object {
        const val COURT_DECISION_NUM_KEY = "court_decision_num"
    }
}
