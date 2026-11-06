package br.edu.fatecpg.appfirebase.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.edu.fatecpg.appfirebase.R
import br.edu.fatecpg.appfirebase.data.ProdutoRepository
import br.edu.fatecpg.appfirebase.model.Produto
import com.google.android.material.textfield.TextInputEditText

class CadastroProdutoActivity : AppCompatActivity(R.layout.activity_cadastro_produto) {

    private lateinit var edtNome: TextInputEditText
    private lateinit var edtCategoria: TextInputEditText
    private lateinit var edtPreco: TextInputEditText
    private lateinit var btnSalvar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.ime())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        edtNome = findViewById(R.id.edt_nome)
        edtCategoria = findViewById(R.id.edt_categoria)
        edtPreco = findViewById(R.id.edt_preco)
        btnSalvar = findViewById(R.id.btn_salvar)

        btnSalvar.setOnClickListener { salvar() }
        findViewById<Button>(R.id.btn_ver_produtos).setOnClickListener {
            startActivity(Intent(this, ListaProdutosActivity::class.java))
        }
    }

    private fun salvar() {
        val nome = edtNome.text.toString().trim()
        val categoria = edtCategoria.text.toString().trim()
        // Aceita tanto "10,50" quanto "10.50".
        val preco = edtPreco.text.toString().trim().replace(',', '.').toDoubleOrNull()

        when {
            nome.isEmpty() -> edtNome.error = getString(R.string.erro_campo_obrigatorio)
            categoria.isEmpty() -> edtCategoria.error = getString(R.string.erro_campo_obrigatorio)
            preco == null || preco < 0 -> edtPreco.error = getString(R.string.erro_preco_invalido)
            else -> {
                btnSalvar.isEnabled = false
                ProdutoRepository.cadastrar(
                    Produto(nome = nome, categoria = categoria, preco = preco),
                    onSucesso = {
                        btnSalvar.isEnabled = true
                        limparCampos()
                        Toast.makeText(this, R.string.msg_produto_salvo, Toast.LENGTH_SHORT).show()
                    },
                    onErro = { e ->
                        btnSalvar.isEnabled = true
                        Log.w(TAG, "Erro ao salvar produto", e)
                        Toast.makeText(this, getString(R.string.msg_erro_salvar, e.message), Toast.LENGTH_LONG).show()
                    }
                )
            }
        }
    }

    private fun limparCampos() {
        edtNome.text = null
        edtCategoria.text = null
        edtPreco.text = null
        edtNome.requestFocus()
    }

    private companion object {
        const val TAG = "CadastroProduto"
    }
}
