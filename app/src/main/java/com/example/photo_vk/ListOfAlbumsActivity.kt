package com.example.photo_vk

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.photo_vk.adapters.AlbumsAdapter
import com.example.photo_vk.api.RetrofitClient
import com.example.photo_vk.api.VKInterface
import com.example.photo_vk.models.albums.Album
import com.example.photo_vk.models.albums.AlbumsGetResponse
import com.example.photo_vk.utils.UploadConstants
import com.example.photo_vk.utils.vkErrorHandler
import com.google.android.flexbox.*
import retrofit2.Response

class ListOfAlbumsActivity : AppCompatActivity() {
    private lateinit var errorLabel: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_of_albums)

        val albumsView: RecyclerView = findViewById(R.id.albums_view)
        errorLabel = findViewById(R.id.albums_placeholder)

        val userId = intent.getStringExtra("user_id")!!

        albumsView.layoutManager = FlexboxLayoutManager(this@ListOfAlbumsActivity).apply {
            justifyContent = JustifyContent.SPACE_AROUND
            alignItems = AlignItems.CENTER
            flexDirection = FlexDirection.ROW
            flexWrap = FlexWrap.WRAP
        }
        val adapter = AlbumsAdapter(userId, this@ListOfAlbumsActivity)
        albumsView.adapter = adapter

        uploadAlbums(adapter, userId, 0, UploadConstants.ALBUM_COUNT)
    }

    fun uploadAlbums(
        adapter: AlbumsAdapter,
        userId: String,
        offset: Int,
        count: Int,
    ) {
        lifecycleScope.launchWhenCreated {
            val api = RetrofitClient.getInstance().create(VKInterface::class.java)
            var response: Response<AlbumsGetResponse>? = null

            try {
                response = api.getAlbums(userId, offset, count, 1)
                if (response.isSuccessful) {
                    if (response.body()?.response?.count == 0) {
                        errorLabel.text = getString(R.string.no_albums)
                        errorLabel.visibility = View.VISIBLE
                    } else {
                        val albums: List<Album> = response.body()!!.response.items
                        adapter.addData(albums)
                        adapter.uploading = false
                        adapter.maxSize = response.body()!!.response.count
                    }
                } else {
                    errorLabel.text = getString(R.string.try_again_error)
                    errorLabel.visibility = View.VISIBLE
                }
            } catch (Ex: Exception) {
                val code = response?.body()?.error?.code
                vkErrorHandler(this@ListOfAlbumsActivity, errorLabel, code, Ex)
            }
        }
    }
}