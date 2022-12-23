package com.example.photo_vk.api

import com.example.photo_vk.models.albums.AlbumsGetResponse
import com.example.photo_vk.models.photos.PhotosGetResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface VKInterface {
    @GET("photos.getAlbums")
    suspend fun getAlbums(
        @Query("owner_id") ownerId: String,
        @Query("offset") offset: Int,
        @Query("count") count: Int,
        @Query("need_covers") needCovers: Int
    ): Response<AlbumsGetResponse>

    @GET("photos.get")
    suspend fun getPhotos(
        @Query("owner_id") ownerId: String,
        @Query("album_id") albumId: Int,
        @Query("offset") offset: Int,
        @Query("count") count: Int,
        @Query("photo_sizes") photoSizes: Int
    ): Response<PhotosGetResponse>
}