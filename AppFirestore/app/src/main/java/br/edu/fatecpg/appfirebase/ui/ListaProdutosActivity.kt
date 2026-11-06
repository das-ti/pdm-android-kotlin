package br.edu.fatecpg.appfirebase.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import br.edu.fatecpg.appfirebase.R
import br.edu.fatecpg.appfirebase.data.ProdutoRepository
import com.google.firebase.firestore.ListenerRegistration

class ListaProdutosActivity : AppCompatActivity(R.layout.activity_lista_produtos) {

    private val adapter = ProdutoAdapter()
    private var listener: ListenerRegistration? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<RecyclerView>(R.id.rv_produtos).adapter = adapter
    }

    // O listener só fica ativo enquanto a tela está visível, evitando vazamento de memória.
    override fun onStart() {
        super.onStart()
        val txtVazio = findViewById<TextView>(R.id.txt_vazio)
        listener = ProdutoRepository.observarTodos(
            onMudanca = { produtos ->
                adapter.submitList(produtos)
                txtVazio.visibility = if (produtos.isEmpty()) View.VISIBLE else View.GONE
            },
            onErro = { e ->
                Log.w(TAG, "Erro ao carregar produtos", e)
                Toast.makeText(this, getString(R.string.msg_erro_carregar, e.message), Toast.LENGTH_LONG).show()
            }
        )
    }

    override fun onStop() {
        listener?.remove()
        listener = null
        super.onStop()
    }

    private companion object {
        const val TAG = "ListaProdutos"
    }
}
