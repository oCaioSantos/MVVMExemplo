package com.example.cotacaomoedas4adsc.di

import com.example.cotacaomoedas4adsc.repository.ICotacaoRepository
import com.example.cotacaomoedas4adsc.repository.remote.CotacaoRepository
import com.example.cotacaomoedas4adsc.viewmodel.MainViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val appModule: Module = module {
    single<ICotacaoRepository> { CotacaoRepository() }
    factory { MainViewModel(get()) }
}