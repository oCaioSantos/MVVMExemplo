package com.example.cotacaomoedas4adsc.model

sealed class MainViewHolder {
    class Loading : MainViewHolder()
    class Content(
        val data: MoedaCotacao
    ) : MainViewHolder()
    class Error(
        val message: String
    ) : MainViewHolder()
}