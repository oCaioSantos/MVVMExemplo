package com.example.cotacaomoedas4adsc.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import com.example.cotacaomoedas4adsc.databinding.ActivityMainBinding
import com.example.cotacaomoedas4adsc.model.MainViewHolder
import com.example.cotacaomoedas4adsc.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        MainViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configurarObservers()
    }

    private fun configurarObservers() {
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
                }

                is MainViewHolder.Error -> {
                    binding.mensagemErroContainer.isVisible = true
                    binding.conteudoContainer.isVisible = false
                    binding.progressBar.isVisible = false
                }
            }
        }
        viewModel.carregarConteudo()
    }
}