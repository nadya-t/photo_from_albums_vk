package com.example.photo_vk.models.photos

import com.google.gson.annotations.SerializedName

data class PhotoSize(
    @SerializedName("url") var url: String
)