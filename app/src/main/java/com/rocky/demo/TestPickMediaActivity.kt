package com.rocky.demo

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.renderscript.Toolkit
import com.rocky.baselib.ext.toBitmap
import com.rocky.demo.databinding.ActivityTestPickMediaBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TestPickMediaActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityTestPickMediaBinding.inflate(layoutInflater)
    }
    val pickMultipleMedia =
        registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(5)) { uris ->
            // Callback is invoked after the user selects media items or closes the
            // photo picker.
            if (uris.isNotEmpty()) {
                Log.d("PhotoPicker", "Number of items selected: ${uris.size}")
                uris.firstOrNull()?.let {
//                    binding.ivImage.setImageURI(it)
                    lifecycleScope.launch(Dispatchers.IO) {
                        it.toBitmap(this@TestPickMediaActivity)?.let { bm ->
                            val blur = Toolkit.blur(bm, 25)
                            withContext(Dispatchers.Main) {
                                binding.ivImage.setImageBitmap(blur)
                            }
                        }
                    }
                }
            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.btnPickMedia.setOnClickListener {
            pickMultipleMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
        }
    }
}