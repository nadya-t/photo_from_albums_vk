package com.example.photo_vk.models.albums

import com.example.photo_vk.models.VKError
import com.google.gson.annotations.SerializedName

data class AlbumsGetResponse(
    @SerializedName("response") var response: AlbumsInfo,
    @SerializedName("error") var error: VKError
)