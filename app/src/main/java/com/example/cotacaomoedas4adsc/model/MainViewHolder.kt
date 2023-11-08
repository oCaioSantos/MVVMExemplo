package com.example.cotacaomoedas4adsc.model

sealed class MainViewHolder {
    class Loading() : MainViewHolder()
    class Content() : MainViewHolder()
    class Error() : MainViewHolder()
}