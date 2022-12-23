package com.example.photo_vk

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.photo_vk.adapters.PhotosAdapter
import com.example.photo_vk.api.RetrofitClient
import com.example.photo_vk.api.VKInterface
import com.example.photo_vk.models.photos.PhotosGetResponse
import com.example.photo_vk.models.photos.Photo
import com.example.photo_vk.utils.UploadConstants
import com.example.photo_vk.utils.vkErrorHandler
import com.google.android.flexbox.*
import retrofit2.Response

class PhotosInAlbumActivity : AppCompatActivity() {
    private lateinit var errorLabel: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photos_in_album)

        val photosView: RecyclerView = findViewById(R.id.photos_view)
        errorLabel = findViewById(R.id.photos_placeholder)

        val albumId: Int = intent.getIntExtra("album_id", -1)
        val userId: String = intent.getStringExtra("user_id")!!
        val albumName: String = intent.getStringExtra("album_name")!!
        title = albumName

        photosView.layoutManager = FlexboxLayoutManager(this@PhotosInAlbumActivity).apply {
            justifyContent = JustifyContent.CENTER
            alignItems = AlignItems.CENTER
            flexDirection = FlexDirection.ROW
            flexWrap = FlexWrap.WRAP
        }
        val adapter = PhotosAdapter(userId, albumId, this@PhotosInAlbumActivity)
        photosView.adapter = adapter

        uploadPictures(adapter, userId, 0, UploadConstants.PHOTO_COUNT, albumId)
    }

    fun uploadPictures(
        adapter: PhotosAdapter,
        userId: String,
        offset: Int,
        count: Int,
        albumId: Int,
    ) {
        lifecycleScope.launchWhenCreated {
            val api = RetrofitClient.getInstance().create(VKInterface::class.java)
            var response: Response<PhotosGetResponse>? = null

            try {
                response = api.getPhotos(
                    userId,
                    albumId,
                    offset,
                    count,
                    1
                )
                if (response.isSuccessful) {
                    if (response.body()?.response?.count == 0) {
                        errorLabel.text = getString(R.string.no_photos)
                        errorLabel.visibility = View.VISIBLE
                    } else {
                        val photos: List<Photo> = response.body()!!.response.items
                        val photosUrl = ArrayList<String>()
                        for (photo in photos) {
                            photosUrl.add(photo.sizes[6].url)
                        }

                        adapter.addData(photosUrl)
                        adapter.uploading = false
                        adapter.maxSize = response.body()!!.response.count
                    }
                } else {
                    errorLabel.text = getString(R.string.try_again_error)
                    errorLabel.visibility = View.VISIBLE
                }
            } catch (Ex: Exception) {
                val code = response?.body()?.error?.code
                vkErrorHandler(this@PhotosInAlbumActivity, errorLabel, code, Ex)
            }
        }
    }
}