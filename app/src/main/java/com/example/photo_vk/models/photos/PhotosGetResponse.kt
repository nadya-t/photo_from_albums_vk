package com.example.photo_vk.models.photos

import com.example.photo_vk.models.VKError
import com.google.gson.annotations.SerializedName

data class PhotosGetResponse(
    @SerializedName("response") var response: PhotosInfo,
    @SerializedName("error") var error: VKError
)