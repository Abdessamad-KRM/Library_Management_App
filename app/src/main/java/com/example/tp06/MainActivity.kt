package com.example.tp06

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var editTitre: EditText
    private lateinit var editPrix: EditText
    private lateinit var editImage: EditText
    private lateinit var checkDisponible: CheckBox
    private lateinit var btnAjouter: Button
    private lateinit var recyclerViewLivres: RecyclerView
    private val listeLivres = mutableListOf<Livre>()
    private lateinit var adapter: AdapterLivres

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTitre = findViewById(R.id.editTitre)
        editPrix = findViewById(R.id.editPrix)
        editImage = findViewById(R.id.editImage)
        checkDisponible = findViewById(R.id.checkDisponible)
        btnAjouter = findViewById(R.id.btnAjouter)
        recyclerViewLivres = findViewById(R.id.recyclerViewLivres)

        listeLivres.add(Livre("L'Étranger", 150.0, "https://m.media-amazon.com/images/I/71da7Rske9L._AC_UF1000,1000_QL80_.jpg", true))
        listeLivres.add(Livre("1984", 180.0, "https://m.media-amazon.com/images/I/71rpa1-kyvL._AC_UF1000,1000_QL80_.jpg", true))
        listeLivres.add(Livre("Le Petit Prince", 120.0, "https://m.media-amazon.com/images/I/71O7WW2AmvL._AC_UF1000,1000_QL80_.jpg", true))

        adapter = AdapterLivres(listeLivres)
        recyclerViewLivres.layoutManager = LinearLayoutManager(this)
        recyclerViewLivres.adapter = adapter

        btnAjouter.setOnClickListener {
            val titre = editTitre.text.toString()
            val prixText = editPrix.text.toString()
            val imageUrl = editImage.text.toString()
            val disponible = checkDisponible.isChecked

            if (titre.isEmpty()) {
                Toast.makeText(this, "Le titre ne doit pas être vide", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (prixText.isEmpty()) {
                Toast.makeText(this, "Le prix ne doit pas être vide", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (imageUrl.isEmpty()) {
                Toast.makeText(this, "L'image URL ne doit pas être vide", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val prix = prixText.toDoubleOrNull()
            if (prix == null || prix <= 0) {
                Toast.makeText(this, "Le prix doit être supérieur à 0", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            listeLivres.add(Livre(titre, prix, imageUrl, disponible))
            adapter.notifyDataSetChanged()

            editTitre.text.clear()
            editPrix.text.clear()
            editImage.text.clear()
            checkDisponible.isChecked = false

            Toast.makeText(this, "Livre ajouté avec succès", Toast.LENGTH_SHORT).show()
        }
    }
}