package com.example.cotacaomoedas4adsc.model

data class MoedaCotacao(
    val alta: String,
    val baixa: String,
    val nomeMoeda: String,
    val precoCompra: String,
    val moedaOrigem: String,
    val moedaDestino: String
)
