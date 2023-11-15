package com.example.cotacaomoedas4adsc.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cotacaomoedas4adsc.api.CotacaoService
import com.example.cotacaomoedas4adsc.api.Rest
import com.example.cotacaomoedas4adsc.model.MainViewHolder
import com.example.cotacaomoedas4adsc.model.MoedaCotacao
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    val state = MutableLiveData<MainViewHolder>()

    private val api by lazy {
        Rest.getInstance().create(CotacaoService::class.java)
    }

    fun carregarConteudo(moedas: String) {
        state.value = MainViewHolder.Loading()
        viewModelScope.launch {
            val request = api.obterUltimaCotacao(moedas)
            request.enqueue(object : Callback<MoedaCotacao> {
                override fun onResponse(
                    call: Call<MoedaCotacao>,
                    response: Response<MoedaCotacao>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { moedaCotacao ->
                            state.value = MainViewHolder.Content(moedaCotacao)
                        }
                    }
                }

                override fun onFailure(call: Call<MoedaCotacao>, t: Throwable) {
                    state.value = MainViewHolder.Error(
                        """
                        Erro na comunicação com a API
                    """.trimIndent()
                    )
                }

            })
        }
    }

}