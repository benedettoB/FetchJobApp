package com.benedetto.data.remote.mappers

import com.benedetto.data.remote.models.ItemsResponse
import com.benedetto.domain.models.Item

fun ItemsResponse.toDomain(): Item {
    return Item(
        id = this.id,
        listId = this.listId,
        name = this.name
    )
}