package com.example.tp06

import android.app.AlertDialog
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class AdapterLivres(private val listeLivres: List<Livre>) : RecyclerView.Adapter<AdapterLivres.LivreViewHolder>() {
    class LivreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageLivre: ImageView = itemView.findViewById(R.id.imageLivre)
        val titreLivre: TextView = itemView.findViewById(R.id.titreLivre)
        val auteurLivre: TextView = itemView.findViewById(R.id.auteurLivre)
        val prixLivre: TextView = itemView.findViewById(R.id.prixLivre)
        val disponibleLivre: CheckBox = itemView.findViewById(R.id.disponibleLivre)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LivreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.livre_item, parent, false)
        return LivreViewHolder(view)
    }

    override fun onBindViewHolder(holder: LivreViewHolder, position: Int) {
        val livre = listeLivres[position]

        holder.titreLivre.text = livre.titre
        holder.prixLivre.text = "Prix: ${livre.prix} DH"
        holder.disponibleLivre.isChecked = livre.disponible

        Glide.with(holder.itemView.context)
            .load(livre.image)
            .placeholder(R.drawable.default_book)
            .error(R.drawable.default_book)
            .into(holder.imageLivre)

        holder.itemView.setOnClickListener {
            val builder = AlertDialog.Builder(holder.itemView.context)
            builder.setTitle("Informations du livre")

            val disponibiliteText = if (livre.disponible) "Disponible" else "Non disponible"
            val disponibiliteColor = if (livre.disponible) Color.GREEN else Color.RED

            val layout = android.widget.LinearLayout(holder.itemView.context)
            layout.orientation = android.widget.LinearLayout.VERTICAL
            layout.setPadding(50, 40, 50, 40)

            val textTitre = TextView(holder.itemView.context)
            textTitre.text = "Titre: ${livre.titre}"
            textTitre.textSize = 16f

            val textPrix = TextView(holder.itemView.context)
            textPrix.text = "\nPrix: ${livre.prix} DH"
            textPrix.textSize = 16f

            val textDisponibilite = TextView(holder.itemView.context)
            val disponibiliteComplet = "\nDisponibilité: $disponibiliteText"
            val spannable = android.text.SpannableString(disponibiliteComplet)

            val debutCouleur = disponibiliteComplet.indexOf(disponibiliteText)
            spannable.setSpan(
                android.text.style.ForegroundColorSpan(disponibiliteColor),
                debutCouleur,
                disponibiliteComplet.length,
                android.text.Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            textDisponibilite.text = spannable
            textDisponibilite.textSize = 16f

            layout.addView(textTitre)
            layout.addView(textPrix)
            layout.addView(textDisponibilite)

            builder.setView(layout)
            builder.setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }

            builder.show()
        }
    }

    override fun getItemCount(): Int {
        return listeLivres.size
    }
}