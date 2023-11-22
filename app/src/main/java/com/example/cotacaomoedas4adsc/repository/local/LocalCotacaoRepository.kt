package com.example.cotacaomoedas4adsc.repository.local

import com.example.cotacaomoedas4adsc.model.MoedaCotacao
import com.example.cotacaomoedas4adsc.repository.ICotacaoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class LocalCotacaoRepository : ICotacaoRepository {

    override suspend fun obterUltimaCotacao(moedas: String)
            : Response<MoedaCotacao> {
        return withContext(Dispatchers.IO) {
            val brlToUsd = moedas.startsWith(
                "brl",
                true
            )
            Response.success(
                MoedaCotacao(
                    alta = if (brlToUsd) "4.90" else "0.25",
                    baixa = if (brlToUsd) "4.80" else "0.17",
                    nomeMoeda = if (brlToUsd) "Real Brasileiro / Dólar Americano" else "Dólar Americano / Real Brasileiro",
                    precoCompra = if (brlToUsd) "4.89" else "0.22",
                    moedaOrigem = if (brlToUsd) "BRL" else "USD",
                    moedaDestino = if (brlToUsd) "USD" else "BRL"
                )
            )
        }
    }

}