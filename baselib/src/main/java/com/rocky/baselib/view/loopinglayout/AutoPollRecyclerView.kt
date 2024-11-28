package com.rocky.baselib.view.loopinglayout

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AutoPollRecyclerView: RecyclerView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )
    private var isPollRunning = false
    private var pollJob: Job? = null

    fun startPoll(scope: LifecycleCoroutineScope,isVertical : Boolean = true) {
        pollJob?.cancel()
        pollJob = scope.launch {
            isPollRunning = true
            while(true) {
                if (isPollRunning) {
                    try {
                        if(isVertical) {
                            this@AutoPollRecyclerView.scrollBy(0, 1)
                        }else{
                            this@AutoPollRecyclerView.scrollBy(1, 0)
                        }
                    } catch (ex: Exception) {
                        ex.printStackTrace()
                    }
                }
                delay(10)
            }
        }
    }

    fun startPollOneByOne(scope: LifecycleCoroutineScope,isVertical : Boolean = true, scrollBy: Int, interval: Int) {
        pollJob?.cancel()
        pollJob = scope.launch {
            isPollRunning = true
            while (true) {
                if (isPollRunning) {
                    try {
                        if (isVertical) {
                            if (this@AutoPollRecyclerView.childCount > 0 && this@AutoPollRecyclerView.getChildAt(0).top != 0) {
                                val off = this@AutoPollRecyclerView.getChildAt(0).top
                                this@AutoPollRecyclerView.scrollBy(0, off)
                            }
                            this@AutoPollRecyclerView.smoothScrollBy(0, scrollBy)
                        } else {
                            if (this@AutoPollRecyclerView.childCount > 0 && this@AutoPollRecyclerView.getChildAt(0).left != 0) {
                                val off = this@AutoPollRecyclerView.getChildAt(0).left
                                this@AutoPollRecyclerView.scrollBy(off, 0)
                            }
                            this@AutoPollRecyclerView.smoothScrollBy(scrollBy, 0)
                        }
                    } catch (ex: Exception) {
                        ex.printStackTrace()
                    }
                }
                delay(interval.toLong())
            }
        }
    }

    fun stopPoll() {
        isPollRunning = false
        pollJob?.cancel()
        pollJob = null
    }

    fun pollRunning(running: Boolean) {
        this.isPollRunning = running
    }

    override fun onTouchEvent(e: MotionEvent): Boolean {
        when (e.getAction()) {
            MotionEvent.ACTION_DOWN -> {
                isPollRunning = false
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_OUTSIDE -> {
                isPollRunning = true
            }
        }
        return super.onTouchEvent(e)
    }
}