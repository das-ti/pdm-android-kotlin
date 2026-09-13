package com.mylabsystems.cadastroalunosapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.random.Random

class MatriculaAluno : AppCompatActivity(R.layout.activity_matricula_aluno) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val txvNomeAluno = findViewById<TextView>(R.id.txv_nome_aluno)
        val txvMatriculaAluno = findViewById<TextView>(R.id.txv_matricula_aluno)
        val fabVoltar = findViewById<FloatingActionButton>(R.id.fab_voltar)
        val btnMatricular = findViewById<Button>(R.id.btn_matricular)

        btnMatricular.setOnClickListener {
            val nomeAluno = intent.getStringExtra("nome_aluno")
            val numeroMatricula = Random.nextInt(1, 1000)

            txvNomeAluno.text = "Nome: $nomeAluno"
            txvMatriculaAluno.text = "Matrícula: $numeroMatricula"
        }

        fabVoltar.setOnClickListener {
            finish()
        }
    }
}