package com.mylabsystems.appimc.dao

import com.mylabsystems.appimc.model.IMC
import kotlin.math.pow


class IMCDAO {
    companion object {
        private var meuIMC = IMC()

        fun calcular(peso: Double, altura: Double): String {
            val imcTotal = peso / (altura.pow(2))

            val mensagem = when {
                imcTotal < 18.5 -> "Você está abaixo do peso ideal."
                imcTotal <= 24.9 -> "Você  está no peso ideal."
                imcTotal <= 29.9 -> "Você está com sobrepeso."
                else -> "Você está com obesidade."
            }

            meuIMC = IMC(peso = peso, altura = altura, imcTotal = imcTotal, mensagem = mensagem)

            return mensagem
        }

        fun buscar(): IMC {
            return meuIMC
        }
    }
}