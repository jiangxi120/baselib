package com.rocky.demo

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

object TestIteratorOnNull {
    val list = listOf(1, 2, 3)
    val flow = MutableSharedFlow<String>()

    fun test() {
//        for (i in list) {
//            Log.d("demo", "TestIteratorOnNull, i: $i")
//        }
        flow.onEach {
            Log.d("demo", "TestIteratorOnNull, it: $it")
        }.launchIn(CoroutineScope(Dispatchers.IO))
    }
}