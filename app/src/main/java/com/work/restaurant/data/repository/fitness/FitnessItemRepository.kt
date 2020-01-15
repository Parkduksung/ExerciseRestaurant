package com.work.restaurant.data.repository.fitness

interface FitnessItemRepository {
    fun getFitnessResult(callback: FitnessItemRepositoryCallback)
}