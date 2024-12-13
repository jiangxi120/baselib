package com.rocky.demo

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.rocky.baselib.ext.hideKeyboard
import com.rocky.baselib.ext.showKeyboard
import com.rocky.demo.databinding.ActivityTestKeyboardBinding

class TestKeyboardActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityTestKeyboardBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.etInput.requestFocus()
        binding.etInput.showKeyboard()
        binding.btOK.setOnClickListener {
            binding.etInput.hideKeyboard()
        }
    }
}