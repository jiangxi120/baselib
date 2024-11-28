package com.rocky.baselib

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rocky.baselib.databinding.ActivityMainBinding
import com.rocky.baselib.ext.setOnFastClickListener
import com.rocky.baselib.R

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.tvText.setOnFastClickListener {

        }
    }
}