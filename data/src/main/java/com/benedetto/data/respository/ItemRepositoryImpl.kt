package com.benedetto.data.respository

import com.benedetto.data.remote.api.ItemsService
import com.benedetto.data.remote.mappers.toDomain
import com.benedetto.data.remote.models.ItemsResponse
import com.benedetto.domain.models.Item
import com.benedetto.domain.repositories.ItemRepository
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import javax.inject.Inject


class ItemsRepositoryImpl @Inject constructor(
    private val okHttpClient: OkHttpClient,
    private val request: Request
) : ItemRepository, ItemsService {

    private val gson = Gson()

    override fun fetchItemsFlow(): Flow<Map<Int, List<Item>>> = flow {
        val itemResponse = fetchItems().toList()
        val items = itemResponse.map { it.toDomain() }
        val data = getGroupedAndSortedItems(items)
        emit(data)
    }.flowOn(Dispatchers.IO)


    override suspend fun fetchItems(): Array<ItemsResponse> {
        try {
            val response = okHttpClient.newCall(request).execute()
            if (response.isSuccessful) {
                val responseBody = response.body?.string()
                if (responseBody != null) {
                    return gson.fromJson(responseBody, Array<ItemsResponse>::class.java)
                } else {
                    throw IOException("Response body is null")
                }
            } else {
                throw IOException("Unexpected code $response")
            }

        } catch (e: Exception) {
            throw Exception(e)
        }
    }

    private fun getGroupedAndSortedItems(items: List<Item>): Map<Int, List<Item>> {
        return items
            .filter { !it.name.isNullOrBlank() }
            .sortedWith(compareBy({ it.listId }, { it.name }))
            .groupBy { it.listId }
    }


}