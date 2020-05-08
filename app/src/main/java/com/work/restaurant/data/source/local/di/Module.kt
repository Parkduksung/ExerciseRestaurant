package com.work.restaurant.data.source.local.di

import com.work.restaurant.data.source.local.eat.EatLocalDataSource
import com.work.restaurant.data.source.local.eat.EatLocalDataSourceImpl
import org.koin.dsl.module


val eatSourceModule =
    module {
        single<EatLocalDataSource> { EatLocalDataSourceImpl(get(), get()) }
    }