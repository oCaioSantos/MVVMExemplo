package com.example.cotacaomoedas4adsc.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cotacaomoedas4adsc.model.MainViewHolder
import com.example.cotacaomoedas4adsc.repository.ICotacaoRepository
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: ICotacaoRepository
) : ViewModel() {

    val state = MutableLiveData<MainViewHolder>()

    fun carregarConteudo(moedas: String) {
        state.value = MainViewHolder.Loading()
        viewModelScope.launch {
            val request = repository.obterUltimaCotacao(moedas)
            if (request.isSuccessful) {
                request.body()?.let { moedaCotacao ->
                    state.value = MainViewHolder.Content(moedaCotacao)
                }
            } else {
                state.value = MainViewHolder.Error("Deu ruim na API")
            }
        }
    }

}