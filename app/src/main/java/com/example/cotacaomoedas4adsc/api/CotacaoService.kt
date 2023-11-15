package com.example.cotacaomoedas4adsc.api

import com.example.cotacaomoedas4adsc.model.MoedaCotacao
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CotacaoService {

    @GET("/ultima/{moedas}")
    fun obterUltimaCotacao(
        @Path("moedas") moedas: String
    ): Call<MoedaCotacao>

}