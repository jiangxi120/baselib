package com.rocky.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rocky.baselib.ext.setOnFastClickListener
import com.rocky.baselib.utils.PackageUtil
import com.rocky.demo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.tvText.setOnFastClickListener {
//            TestKeyboardActivity.start(this)
//            testLaunchPkg(this)
            TestIteratorOnNull.test()
        }
    }

}