package com.mylabsystems.cadastroalunosapp

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
        val edtNomeAluno = findViewById<EditText>(R.id.edt_nome_aluno)
        val btnSalvar = findViewById<Button>(R.id.btn_salvar)
        val fabAvancar = findViewById<FloatingActionButton>(R.id.fab_avancar)
        
        var nomeSalvo = ""
        
        btnSalvar.setOnClickListener {
            val nomeAluno = edtNomeAluno.text.toString()

            nomeSalvo = nomeAluno

            edtNomeAluno.text.clear()
            Toast.makeText(this, "Nome do aluno $nomeAluno salvo.", Toast.LENGTH_SHORT).show()
            Toast.makeText(this, "Agora realize a matrícula!", Toast.LENGTH_SHORT).show()
        }
        fabAvancar.setOnClickListener {
            val intent = Intent(this, MatriculaAluno::class.java)
            intent.putExtra("nome_aluno", nomeSalvo)
            startActivity(intent)
        }
    }
}