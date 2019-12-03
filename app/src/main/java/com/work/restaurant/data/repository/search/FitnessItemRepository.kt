package com.work.restaurant.data.repository.search

interface FitnessItemRepository {

    fun getFitnessResult(callback : FitnessItemRepositoryCallback)
}