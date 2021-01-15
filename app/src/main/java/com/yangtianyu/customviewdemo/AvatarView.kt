package com.yangtianyu.customviewdemo

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

private val IMAGE_PADDING = 50f.dp
private val IMAGE_WIDTH = 200f.dp
private val XFERMODE = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)


class AvatarView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val boundaryPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 10f.dp
        color = Color.parseColor("#999999")
    }
    private val rectF = RectF(
        IMAGE_PADDING, IMAGE_PADDING, IMAGE_PADDING + IMAGE_WIDTH,
        IMAGE_PADDING + IMAGE_WIDTH
    )

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.apply {
            val count = saveLayer(rectF, paint)
            drawOval(rectF,paint)
            paint.xfermode = XFERMODE
            drawBitmap(
                getAvatar(IMAGE_WIDTH.toInt()),
                IMAGE_PADDING,
                IMAGE_PADDING,
                paint
            )
            paint.xfermode = null
            restoreToCount(count)
            drawOval(rectF,boundaryPaint)
        }
    }

    private fun getAvatar(width: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.drawable.head, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(resources, R.drawable.head, options)
    }


}