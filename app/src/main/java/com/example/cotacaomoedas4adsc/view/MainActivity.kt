package com.example.cotacaomoedas4adsc.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Spinner
import androidx.core.view.isNotEmpty
import androidx.core.view.isVisible
import com.example.cotacaomoedas4adsc.databinding.ActivityMainBinding
import com.example.cotacaomoedas4adsc.model.MainViewHolder
import com.example.cotacaomoedas4adsc.repository.local.LocalCotacaoRepository
import com.example.cotacaomoedas4adsc.repository.remote.CotacaoRepository
import com.example.cotacaomoedas4adsc.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        MainViewModel(
            CotacaoRepository()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configurarObservers()
    }

    private fun podeFazerRequisicao(): Boolean {
        val primeiroCampo = binding.campoMoedaOrigem
        val segundoCampo = binding.campoMoedaDestino
        val camposPreenchidos = primeiroCampo.isNotEmpty() && segundoCampo.isNotEmpty()
        val moedasDiferentes =
            primeiroCampo.selectedItem.toString() != segundoCampo.selectedItem.toString()
        return camposPreenchidos && moedasDiferentes
    }

    private fun formatarMoedasParaRequisicao(
        moedaOrigem: String, moedaDestino: String
    ): String {
        return "${moedaOrigem}-${moedaDestino}"
    }

    private fun configurarListenerItemSelecionado(spinner: Spinner) {
        spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (podeFazerRequisicao()) {
                    val moedas = formatarMoedasParaRequisicao(
                        binding.campoMoedaOrigem.selectedItem.toString(),
                        binding.campoMoedaDestino.selectedItem.toString()
                    )
                    viewModel.carregarConteudo(
                        moedas
                    )
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}

        }
    }

    private fun configurarSpinnersListeners() {
        with(binding.campoMoedaOrigem) { configurarListenerItemSelecionado(this) }
        with(binding.campoMoedaDestino) { configurarListenerItemSelecionado(this) }
    }

    private fun configurarObservers() {
        configurarSpinnersListeners()
        viewModel.state.observe(this) { view ->
            when (view) {
                is MainViewHolder.Loading -> {
                    binding.progressBar.isVisible = true
                    binding.mensagemErroContainer.isVisible = false
                    binding.conteudoContainer.isVisible = false
                }

                is MainViewHolder.Content -> {
                    binding.conteudoContainer.isVisible = true
                    binding.mensagemErroContainer.isVisible = false
                    binding.progressBar.isVisible = false

                    binding.nomeMoedaLabel.text = view.data.nomeMoeda
                    binding.variacaoAlta.text = view.data.alta
                    binding.variacaoBaixa.text = view.data.baixa
                }

                is MainViewHolder.Error -> {
                    binding.mensagemErroContainer.isVisible = true
                    binding.conteudoContainer.isVisible = false
                    binding.progressBar.isVisible = false
                }
            }
        }
    }
}