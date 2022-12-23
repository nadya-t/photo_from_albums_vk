package com.example.photo_vk.models.photos

import com.google.gson.annotations.SerializedName

data class Photo(
    @SerializedName("sizes") var sizes: List<PhotoSize>
)