package com.mylabsystems.cadastrolivros

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val edtTituloLivro = findViewById<EditText>(R.id.edt_titulo_livro)
        val edtAutorLivro = findViewById<EditText>(R.id.edt_autor_livro)
        val btnCadastrar = findViewById<Button>(R.id.btn_cadastrar)
        val fabAvancar = findViewById<FloatingActionButton>(R.id.fab_avancar)
        var titulocadastrado = ""
        var autorcadastrado = ""
        btnCadastrar.setOnClickListener {
            val tituloLivro = edtTituloLivro.text.toString()
            val autorLivro = edtAutorLivro.text.toString()

            titulocadastrado = tituloLivro
            autorcadastrado = autorLivro

            edtTituloLivro.text.clear()
            edtAutorLivro.text.clear()
            Toast.makeText(this, "Livro cadastrado com sucesso!", Toast.LENGTH_SHORT).show()
        }
        fabAvancar.setOnClickListener {
            val intent = Intent(this, DetalhesLivro::class.java)
            intent.putExtra("titulo_livro",titulocadastrado)
            intent.putExtra("autor_livro",autorcadastrado)
            startActivity(intent)
        }
    }
}