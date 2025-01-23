package com.benedetto.data.remote.api


import com.benedetto.data.remote.models.ItemsResponse

interface ItemsService {

    suspend fun fetchItems(): Array<ItemsResponse>
}