package br.edu.fatecpg.appcompose

fun calcularPrecoComDesconto (preco: Double, percentual: Double): Double {
    val desconto = preco * percentual / 100.0
    return preco - desconto
}