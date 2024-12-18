package com.rocky.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rocky.baselib.ext.setOnFastClickListener
import com.rocky.baselib.ext.startActivity
import com.rocky.demo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.tvText1.setOnFastClickListener {
//            this.startActivity(TestKeyboardActivity::class.java)
//            this.startActivity(TestWebViewActivity::class.java)
//            startActivity(TestMediaStoreImageActivity::class.java)
//            this.startActivity(TestShareToAppActivity::class.java)
            this.startActivity(TestPickMediaActivity::class.java)
        }
        binding.tvText2.setOnClickListener {
//            testLaunchPkg(this)
//            TestIteratorOnNull.test()
//            TestUriParse.test()
        }
    }

}