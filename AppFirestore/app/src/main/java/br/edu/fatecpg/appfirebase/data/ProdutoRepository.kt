package br.edu.fatecpg.appfirebase.data

import br.edu.fatecpg.appfirebase.model.Produto
import com.google.firebase.Firebase
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.firestore

// Singleton + Facade: único ponto de acesso à coleção "Produtos" no Firestore.
object ProdutoRepository {

    private const val COLECAO = "Produtos"

    private val produtos by lazy { Firebase.firestore.collection(COLECAO) }

    // add() gera o ID do documento automaticamente.
    fun cadastrar(produto: Produto, onSucesso: () -> Unit, onErro: (Exception) -> Unit) {
        produtos.add(produto)
            .addOnSuccessListener { onSucesso() }
            .addOnFailureListener(onErro)
    }

    // Observer: o callback é chamado sempre que a coleção muda (tempo real).
    fun observarTodos(onMudanca: (List<Produto>) -> Unit, onErro: (Exception) -> Unit): ListenerRegistration =
        produtos.orderBy("nome").addSnapshotListener { snapshot, erro ->
            if (erro != null) {
                onErro(erro)
                return@addSnapshotListener
            }
            onMudanca(snapshot?.toObjects(Produto::class.java).orEmpty())
        }
}
