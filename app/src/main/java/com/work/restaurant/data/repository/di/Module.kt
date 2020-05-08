package com.work.restaurant.data.repository.di

import com.work.restaurant.data.repository.eat.EatRepository
import com.work.restaurant.data.repository.eat.EatRepositoryImpl
import org.koin.dsl.module


val eatRepositoryModule =
    module {
        single<EatRepository> { EatRepositoryImpl(get()) }
    }