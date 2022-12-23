package com.example.photo_vk.models.albums

import com.google.gson.annotations.SerializedName

data class AlbumsInfo(
    @SerializedName("count") var count: Int,
    @SerializedName("items") var items: List<Album>
)