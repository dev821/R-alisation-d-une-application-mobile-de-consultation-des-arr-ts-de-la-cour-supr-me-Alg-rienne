// src/main/java/com/example/projet_tdm/CourtDecisionAdapter.kt
package com.example.projet_tdm
import android.content.Intent
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.projet_tdm.entity.CourtDecision

class CourtDecisionAdapter :
    ListAdapter<CourtDecision, CourtDecisionAdapter.CourtDecisionViewHolder>(CourtDecisionDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourtDecisionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_court_decision, parent, false)
        return CourtDecisionViewHolder(view)
    }

    override fun onBindViewHolder(holder: CourtDecisionViewHolder, position: Int) {
        val courtDecision = getItem(position)
        holder.bind(courtDecision)
        // Set click listener to open CourtDecisionDetailActivity
        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, CourtDecisionDetailActivity::class.java)
            intent.putExtra(CourtDecisionDetailActivity.COURT_DECISION_NUM_KEY, courtDecision.NumArret)
            it.context.startActivity(intent)
        }
    }

    inner class CourtDecisionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val txtCourtDecision: TextView = itemView.findViewById(R.id.txtCourtDecision)
//        private val btnLike: ImageButton = itemView.findViewById(R.id.btnLike)

        fun bind(courtDecision: CourtDecision) {
            val concatenatedText = "القرار رقم: ${courtDecision.NumArret} الصادر عن :${courtDecision.ChambreArret} بتاريخ ${courtDecision.DateArret}"

            // Create a SpannableStringBuilder to apply styles
            val spannableBuilder = SpannableStringBuilder(concatenatedText)

            // Find the start and end indices of the variables you want to make bold
            val startBold1 = concatenatedText.indexOf(courtDecision.NumArret)
            val endBold1 = startBold1 + courtDecision.NumArret.length

            val startBold2 = concatenatedText.indexOf(courtDecision.ChambreArret)
            val endBold2 = startBold2 + courtDecision.ChambreArret.length

            val startBold3 = concatenatedText.indexOf(courtDecision.DateArret)
            val endBold3 = startBold3 + courtDecision.DateArret.length

            // Apply bold style to the specified parts
            spannableBuilder.setSpan(StyleSpan(android.graphics.Typeface.BOLD), startBold1, endBold1, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
            spannableBuilder.setSpan(StyleSpan(android.graphics.Typeface.BOLD), startBold2, endBold2, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
            spannableBuilder.setSpan(StyleSpan(android.graphics.Typeface.BOLD), startBold3, endBold3, Spannable.SPAN_INCLUSIVE_INCLUSIVE)

            // Set the styled text to the TextView
            txtCourtDecision.text = spannableBuilder
        }
    }

    class CourtDecisionDiffCallback : DiffUtil.ItemCallback<CourtDecision>() {
        override fun areItemsTheSame(oldItem: CourtDecision, newItem: CourtDecision): Boolean {
            return oldItem.NumArret == newItem.NumArret
        }

        override fun areContentsTheSame(oldItem: CourtDecision, newItem: CourtDecision): Boolean {
            return oldItem == newItem
        }
    }
}
