package com.rocky.baselib.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Point
import android.util.AttributeSet
import android.view.View
import com.rocky.baselib.R
import kotlin.math.max
import kotlin.math.sqrt

class StripView : View {
    companion object {
        const val SHAPE_RECT = 0
        const val SHAPE_ROUND = 1
        const val DIRECTION_TOPLEFT_2_BOTTOMRIGHT = 0
        const val DIRECTION_BOTTOMLEFT_2_TOPRIGHT = 1
    }

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initAttrib(attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initAttrib(attrs)
    }

    private var shape = SHAPE_RECT
    private var direction = DIRECTION_TOPLEFT_2_BOTTOMRIGHT
    private var stripWidth = 10 * 3
    private var stripColor = 0
    private var duration = 300
    private var interval = 0

    private fun initAttrib(attrs: AttributeSet?) {
        val ta: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.StripView)
        try {
            stripWidth = ta.getDimension(R.styleable.StripView_strip_width, 10f).toInt()
            stripColor = ta.getColor(
                R.styleable.StripView_strip_color,
                Color.parseColor("#88FFFFFF")
            )
            duration = ta.getInteger(
                R.styleable.StripView_strip_duration,
                300
            )
            interval = ta.getInteger(R.styleable.StripView_strip_interval, 0)
            shape = ta.getInt(R.styleable.StripView_strip_shape, SHAPE_RECT)
            direction =
                ta.getInt(R.styleable.StripView_strip_direction, DIRECTION_TOPLEFT_2_BOTTOMRIGHT)
        } finally {
            ta.recycle()
        }
    }

    val paint by lazy {
        Paint().apply {
            this.color = Color.parseColor("#88FFFFFF")
            this.strokeWidth = (6 * 3).toFloat()
        }
    }
    val midPoint1 = Point()
    val midPoint2 = Point()
    var animPercent = 0f
    val clipPath = Path()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val off = (stripWidth / sqrt(2.0)).toInt()
        if (direction == DIRECTION_TOPLEFT_2_BOTTOMRIGHT) {
            midPoint1.set(-off, -off)
            midPoint2.set(measuredWidth + off, measuredHeight + off)
        } else {
            midPoint1.set(-off, measuredHeight + off)
            midPoint2.set(measuredWidth + off, -off)
        }

        if (shape == SHAPE_RECT) {
            clipPath.addRect(
                0f,
                0f,
                measuredWidth.toFloat(),
                measuredHeight.toFloat(),
                Path.Direction.CCW
            )
        } else {
            val radius = max(measuredWidth, measuredHeight)
            clipPath.addCircle(
                (measuredWidth / 2).toFloat(),
                (measuredHeight / 2).toFloat(),
                (radius / 2).toFloat(),
                Path.Direction.CCW
            )
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (isStripping()) {
            val savedCount = canvas.save()
            canvas.clipPath(clipPath)

            val midX = midPoint1.x + animPercent * (midPoint2.x - midPoint1.x)
            val midY = midPoint1.y + animPercent * (midPoint2.y - midPoint1.y)
            if (direction == DIRECTION_TOPLEFT_2_BOTTOMRIGHT) {
                canvas.drawLine(
                    midX - width / 2,
                    midY + height / 2,
                    midX + width / 2,
                    midY - height / 2,
                    paint
                )

            } else {
                canvas.drawLine(
                    midX - width / 2,
                    midY - height / 2,
                    midX + width / 2,
                    midY + height / 2,
                    paint
                )
            }

            canvas.restoreToCount(savedCount)
        }
    }

    private var animator: Animator? = null
    fun startStrip(startDelay: Long = 0) {
        stopStrip()
        ValueAnimator.ofFloat(0f, 1f).setDuration(duration.toLong()).apply {
            animator = this
            if (startDelay > 0) {
                this.startDelay = startDelay
            }
            this.addUpdateListener {
                animPercent = it.animatedValue as Float
                invalidate()
            }
            this.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    if (interval > 0) {
                        startStrip(interval.toLong())
                    }
                }
            })
        }.start()
    }

    fun stopStrip() {
        animator?.removeAllListeners()
        animator?.cancel()
    }

    fun isStripping(): Boolean {
        return animator != null && animator?.isRunning == true
    }
}