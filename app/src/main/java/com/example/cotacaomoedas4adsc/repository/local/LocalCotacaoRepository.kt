package com.example.cotacaomoedas4adsc.repository.local

import com.example.cotacaomoedas4adsc.model.MoedaCotacao
import com.example.cotacaomoedas4adsc.repository.ICotacaoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class LocalCotacaoRepository: ICotacaoRepository {

    override suspend fun obterUltimaCotacao(moedas: String)
    : Response<MoedaCotacao> {
        return withContext(Dispatchers.IO) {
            Response.success(
                MoedaCotacao(
                    alta = "4.90",
                    baixa = "4.80",
                    nomeMoeda = "Real Brasileiro / Dólar Americano",
                    precoCompra = "4.89",
                    moedaOrigem = "BRL",
                    moedaDestino = "USD"
                )
            )
        }
    }

}