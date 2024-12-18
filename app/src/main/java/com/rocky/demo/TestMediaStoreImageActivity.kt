package com.rocky.demo

import android.Manifest
import android.content.ContentProvider
import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentResolverCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import java.text.SimpleDateFormat
import java.util.Date
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TestMediaStoreImageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_test_media_store_image)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        MediaStoreLoader.loadImages(this)
        MediaStoreLoader.localImagesStateFlow.onEach {
            Log.d("testdemo", "$it")
        }
    }
}

object MediaStoreLoader {
    private val _localImagesStateFlow = MutableStateFlow<List<MediaStoreImage>>(emptyList())
    val localImagesStateFlow = _localImagesStateFlow.asStateFlow()

    fun loadImages(activity: FragmentActivity) {
        activity.lifecycleScope.launch {
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED) {
                _localImagesStateFlow.value = _loadImages(activity)
            } else {
                activity.registerForActivityResult(
                    ActivityResultContracts.RequestPermission()
                ) { isGranted: Boolean ->
                    if (isGranted) {
                        // Permission is granted. Continue the action or workflow in your
                        // app.
                        activity.lifecycleScope.launch {
                            _localImagesStateFlow.value = _loadImages(activity)
                        }
                    } else {
                        // Explain to the user that the feature is unavailable because the
                        // feature requires a permission that the user has denied. At the
                        // same time, respect the user's decision. Don't link to system
                        // settings in an effort to convince the user to change their
                        // decision.
                    }
                }.launch(Manifest.permission.READ_MEDIA_IMAGES)
            }
        }
    }


    private suspend fun _loadImages(context: Context): List<MediaStoreImage> {
        val images = mutableListOf<MediaStoreImage>()

        withContext(Dispatchers.IO) {
            val projection = arrayOf(
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATE_ADDED
            )

            val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

            val collection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_INTERNAL)
            } else {
                MediaStore.Images.Media.INTERNAL_CONTENT_URI
            }

//            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            context.contentResolver.query(
                collection,
                projection,
                null,
                null,
                sortOrder
            )?.use { cursor ->

                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                val dateModifiedColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED)
                val displayNameColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)

                Log.i("testdemo", "Found ${cursor.count} images")
                while (cursor.moveToNext()) {

                    // Here we'll use the column indexs that we found above.
                    val id = cursor.getLong(idColumn)
                    val dateModified =
                        Date(TimeUnit.SECONDS.toMillis(cursor.getLong(dateModifiedColumn)))
                    val displayName = cursor.getString(displayNameColumn)

                    val contentUri = ContentUris.withAppendedId(
                        collection,
                        id
                    )

                    val image = MediaStoreImage(id, displayName, dateModified, contentUri)
                    images += image

                    // For debugging, we'll output the image objects we create to logcat.
                    Log.v("testdemo", "Added image: $image")
                }
            }
        }

        return images
    }
}

data class MediaStoreImage(
    val id: Long,
    val displayName: String,
    val dateAdded: Date,
    val contentUri: Uri
) {
    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<MediaStoreImage>() {
            override fun areItemsTheSame(oldItem: MediaStoreImage, newItem: MediaStoreImage) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: MediaStoreImage, newItem: MediaStoreImage) =
                oldItem == newItem
        }
    }
}