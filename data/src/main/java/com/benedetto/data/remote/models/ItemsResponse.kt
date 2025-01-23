package com.benedetto.data.remote.models

import com.google.gson.annotations.SerializedName

data class ItemsResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("listId")
    val listId: Int,
    @SerializedName("name")
    val name: String? = null
)