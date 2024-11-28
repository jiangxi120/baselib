package com.rocky.baselib.view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.rocky.baselib.R
import com.rocky.baselib.ext.dp

class HollowView : View {

    private var maskBitmap: Bitmap? = null
    private var maskCanvas: Canvas? = null
    private var screenRect: Rect? = null

    val defaultShadowColor = R.color.color_black80
    val paint by lazy {
        Paint()
    }
    val holoPaint by lazy {
        Paint().apply {
            this.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
            this.flags = Paint.ANTI_ALIAS_FLAG
        }
    }
    val rect = RectF(0f, 0f, 0f, 0f)
    val rectInfoList = mutableListOf<RectInfo>()
    var padding = 0f
    var corner = 0f
    var color = 0

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs)
    }

    private fun init(context: Context?, attrs: AttributeSet?) {
        val a: TypedArray? = context?.obtainStyledAttributes(attrs, R.styleable.HollowView)

        corner = a?.getDimensionPixelSize(
            R.styleable.HollowView_corner,
            10.dp
        )?.toFloat() ?: 0f

        padding = a?.getDimensionPixelSize(
            R.styleable.HollowView_padding,
            10.dp
        )?.toFloat() ?: 0f

        color = a?.getColor(
            R.styleable.HollowView_shadowColor,
            resources.getColor(defaultShadowColor)
        ) ?:0

        a?.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        recreateBitmap()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        recreateBitmap()
    }

    private fun recreateBitmap() {
        if (measuredWidth > 0 && measuredHeight > 0) {
            maskBitmap = Bitmap.createBitmap(measuredWidth, measuredHeight, Bitmap.Config.ARGB_8888)
            maskCanvas = Canvas(maskBitmap!!)
            screenRect = Rect(0, 0, measuredWidth, measuredHeight)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        maskBitmap ?: return
        maskCanvas ?: return
        screenRect ?: return

        maskBitmap!!.eraseColor(Color.TRANSPARENT)
        maskCanvas!!.drawColor(color)
        if (rect.width() > 0 && rect.height() > 0) {
            if (drawCircle) {
                maskCanvas!!.drawRoundRect(rect, rect.width() / 2, rect.height() / 2, holoPaint)
            } else {
                maskCanvas!!.drawRoundRect(rect, corner, corner, holoPaint)
            }
        }
        rectInfoList.forEach {
            if (it.rect.width() > 0 && it.rect.height() > 0) {
                if (it.drawCircle) {
                    maskCanvas!!.drawRoundRect(it.rect, it.rect.width() / 2, it.rect.height() / 2, holoPaint)
                } else {
                    maskCanvas!!.drawRoundRect(it.rect, it.corner, it.corner, holoPaint)
                }
            }
        }
        canvas.drawBitmap(maskBitmap!!, screenRect, screenRect!!, paint)
    }

    fun updateRect(rectF: RectF) {
        this.rect.set(rectF)
        drawCircle = false
        this.invalidate()
    }

    private var drawCircle = false

    fun updateCircle(rectF: RectF) {
        if (rectF.width() > rectF.height()) {
            rectF.inset((rectF.width() - rectF.height()) / 2, 0f)
        } else if (rectF.width() < rectF.height()) {
            rectF.inset(0f, (rectF.height() - rectF.width()) / 2)
        }
        this.rect.set(rectF)
        drawCircle = true
        this.invalidate()
    }

    fun updateRectInfoList(rectInfos: List<RectInfo>) {
        rectInfoList.clear()
        rectInfos.forEach {
            rectInfoList.add(it.copy(
                corner = if (it.corner == 0f) corner else it.corner
            ))
        }
        this.invalidate()
    }

}

data class RectInfo(
    val rect: RectF,
    val corner: Float = 0f,
    val drawCircle: Boolean = false
)