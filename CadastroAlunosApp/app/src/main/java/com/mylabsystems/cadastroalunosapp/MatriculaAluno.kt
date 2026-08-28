package com.mylabsystems.cadastroalunosapp
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MatriculaAluno : AppCompatActivity(R.layout.activity_matricula_aluno) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val txv = findViewById<TextView>(R.id.txv)
        val txvAutorLivro = findViewById<TextView>(R.id.txv_autor_livro)
        val fabVoltar = findViewById<FloatingActionButton>(R.id.fab_voltar)

        val tituloCadastrado = intent.getStringExtra("titulo_livro")
        val autorCadastrado = intent.getStringExtra("autor_livro")
        txvTituloLivro.text = "Título: $tituloCadastrado"
        txvAutorLivro.text = "Autor: $autorCadastrado"

        fabVoltar.setOnClickListener {
            finish()
        }
    }
}