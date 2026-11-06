package br.edu.fatecpg.appfirebase.model

import com.google.firebase.firestore.DocumentId

// Valores padrão são obrigatórios: o Firestore precisa de um construtor vazio para o toObject().
data class Produto(
    @DocumentId val id: String = "",
    val nome: String = "",
    val categoria: String = "",
    val preco: Double = 0.0
)
