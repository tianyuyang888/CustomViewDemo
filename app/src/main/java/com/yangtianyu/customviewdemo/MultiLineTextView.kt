package com.yangtianyu.customviewdemo

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.view.View

private val IMAGE_WIDTH = 100.dp
private val IMAGE_PADDING = 100.dp

class MultiLineTextView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val str =
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi hendrerit rutrum dui sit amet auctor. Interdum et malesuada fames ac ante ipsum primis in faucibus. Vivamus viverra ante ut nibh aliquet tincidunt. Fusce in varius tellus. Morbi euismod pulvinar auctor. Vivamus viverra tortor at eleifend maximus. Vestibulum gravida orci eget enim tristique, ut condimentum diam vestibulum. Proin fermentum nulla blandit erat varius iaculis. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Duis aliquam accumsan arcu, et congue enim vehicula quis." +
                "Curabitur maximus nibh ac dolor aliquet ultrices. Praesent diam dolor, feugiat vitae sapien nec, lacinia consequat sem. Pellentesque vitae placerat ex. Fusce vel malesuada libero, sed ornare nunc. Sed luctus odio justo, at tempor sem congue quis. Nunc fermentum erat a interdum vulputate. Donec efficitur egestas lacus ac sollicitudin. Vestibulum id lectus a arcu venenatis accumsan at vitae diam. Maecenas iaculis non lectus at elementum. Donec vehicula ligula quis fermentum vestibulum. Vivamus vitae hendrerit velit."

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val fontMetrics = Paint.FontMetrics()

    private val textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 20.dp
    }
    private val measureWidth = floatArrayOf(0f)


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.apply {
            //1、画图片
            drawBitmap(
                getBitmapByWidth(IMAGE_WIDTH.toInt()),
                width - IMAGE_WIDTH,
                IMAGE_PADDING,
                paint
            )
            //2、画文字
            textPaint.getFontMetrics(fontMetrics)
            var start = 0
            var count: Int
            var fontPadding = -fontMetrics.top
            var maxWidth = width.toFloat()
            Log.d("measure", "IMAGE_PADDING:$IMAGE_PADDING")
            while (start < str.length) {
                Log.d("measure", "fontPadding:$fontPadding")
                maxWidth =
                    if (fontPadding + fontMetrics.bottom < IMAGE_PADDING || fontPadding + fontMetrics.top > IMAGE_PADDING + IMAGE_WIDTH) {
                        width.toFloat()
                    } else {
                        width - IMAGE_WIDTH
                    }
                count = textPaint.breakText(
                    str,
                    start,
                    str.length,
                    true,
                    maxWidth,
                    measureWidth
                )
                drawText(str, start, start + count, 0f, fontPadding, textPaint)
                start += count
                fontPadding += textPaint.fontSpacing
            }

        }
    }

    private fun getBitmapByWidth(width: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.drawable.head, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(resources, R.drawable.head, options)
    }


}