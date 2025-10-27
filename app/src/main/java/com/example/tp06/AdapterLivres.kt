package com.example.tp06

import android.app.AlertDialog
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
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
            val context = holder.itemView.context
            val dispoText = if (livre.disponible) "Disponible" else "Non disponible"
            val dispoColor = if (livre.disponible) Color.GREEN else Color.RED

            val message = "\nTitre : ${livre.titre}\n\nPrix : ${livre.prix} DH\n\nDisponibilité : $dispoText"

            val spannable = SpannableString(message).apply {
                val start = message.indexOf(dispoText)
                setSpan(ForegroundColorSpan(dispoColor), start, start + dispoText.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }

            AlertDialog.Builder(context)
                .setTitle("Informations du livre")
                .setMessage(spannable)
                .setPositiveButton("OK", null)
                .show()
        }

    }

    override fun getItemCount(): Int {
        return listeLivres.size
    }
}