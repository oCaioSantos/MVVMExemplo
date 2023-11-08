package com.example.cotacaomoedas4adsc.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cotacaomoedas4adsc.model.MainViewHolder
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val state = MutableLiveData<MainViewHolder>()

    fun carregarConteudo() {
        state.value = MainViewHolder.Loading()
        viewModelScope.launch {
            delay(3000)
            state.value = MainViewHolder.Content()
        }
    }

}