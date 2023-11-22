package com.example.cotacaomoedas4adsc.repository.remote

import com.example.cotacaomoedas4adsc.api.CotacaoService
import com.example.cotacaomoedas4adsc.api.Rest
import com.example.cotacaomoedas4adsc.model.MoedaCotacao
import com.example.cotacaomoedas4adsc.repository.ICotacaoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.await

class CotacaoRepository: ICotacaoRepository {

    private val api by lazy {
        Rest.getInstance().create(CotacaoService::class.java)
    }

    override suspend fun obterUltimaCotacao(moedas: String)
    : Response<MoedaCotacao> {
        return withContext(Dispatchers.IO) {
            try {
                val request = api.obterUltimaCotacao(moedas)
                val response = request.await()
                Response.success(response)
            } catch(e: HttpException) {
                Response.error(e.code(), e.response()?.errorBody())
            }
        }
    }

}