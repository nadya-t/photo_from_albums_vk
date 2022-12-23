package com.example.photo_vk.models.photos

import com.google.gson.annotations.SerializedName

data class PhotosInfo(
    @SerializedName("count") var count: Int,
    @SerializedName("items") var items: List<Photo>
)