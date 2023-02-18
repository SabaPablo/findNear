package com.doce.cactus.saba.findnear.koin

import com.doce.cactus.saba.findnear.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel() }
}

