package com.benedetto.domain.usecases

import com.benedetto.domain.models.Item
import com.benedetto.domain.repositories.ItemRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetItemsUseCase @Inject constructor(
    private val repository: ItemRepository
) {
    operator fun invoke(): Flow<Map<Int, List<Item>>> {
        return repository.fetchItemsFlow()
    }
}