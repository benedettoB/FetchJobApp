package com.benedetto.domain.repositories

import com.benedetto.domain.models.Item
import kotlinx.coroutines.flow.Flow


interface ItemRepository {

    fun fetchItemsFlow(): Flow<Map<Int, List<Item>>>

}