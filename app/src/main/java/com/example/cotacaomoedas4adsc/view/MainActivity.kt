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
import com.example.cotacaomoedas4adsc.di.appModule
import com.example.cotacaomoedas4adsc.model.MainViewHolder
import com.example.cotacaomoedas4adsc.viewmodel.MainViewModel
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel: MainViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        startKoin {
            androidContext(this@MainActivity)
            modules(appModule)
        }
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

    private fun formatarMoedaOrigem(
        moedaDestino: String
    ): String {
        return "1 $moedaDestino"
    }

    private fun formatarMoedaDestino(
        valor: String, moedaOrigem: String
    ): String {
        return "$valor $moedaOrigem"
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
                    with(view.data) {
                        binding.nomeMoedaLabel.text = nomeMoeda
                        binding.variacaoAlta.text = alta
                        binding.variacaoBaixa.text = baixa
                        binding.moedaOrigem.text =
                            formatarMoedaOrigem(moedaOrigem)
                        binding.moedaDestino.text =
                            formatarMoedaDestino(precoCompra, moedaDestino)
                    }
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