package com.example.photo_vk.models

import com.google.gson.annotations.SerializedName

data class VKError(
    @SerializedName("error_code") val code: Int
)
