package com.example.cotacaomoedas4adsc.repository

import com.example.cotacaomoedas4adsc.model.MoedaCotacao
import retrofit2.Response

interface ICotacaoRepository {

    suspend fun obterUltimaCotacao(moedas: String)
        : Response<MoedaCotacao>

}