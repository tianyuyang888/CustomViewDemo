package com.yangtianyu.customviewdemo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.content.res.ResourcesCompat

private val RADIUS = 150.dp
private val STROKE_WIDTH = 20.dp

class SportView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private val str = "abpg"

    private val rectF = RectF()

    private val fontMetrics = Paint.FontMetrics()

    private val firstPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = Color.parseColor("#999999")
        strokeWidth = STROKE_WIDTH
    }
    private val secondPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = Color.parseColor("#FF34C2")
        strokeWidth = STROKE_WIDTH
        strokeCap = Paint.Cap.ROUND
    }
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 80.dp
        color = Color.parseColor("#FF34C2")
        typeface = ResourcesCompat.getFont(context, R.font.axl)
        textAlign = Paint.Align.CENTER
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        rectF.set(
            width / 2f - RADIUS,
            height / 2f - RADIUS,
            width / 2f + RADIUS,
            height / 2f + RADIUS
        )

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.apply {
            //1、画背景圆
            drawCircle(width / 2f, height / 2f, RADIUS, firstPaint)
            //2、画进度圆弧
            drawArc(rectF, 270f, 200f, false, secondPaint)
            //3、画文字
            textPaint.getFontMetrics(fontMetrics)
            drawText(
                str,
                width / 2f,
                height / 2f - (fontMetrics.ascent + fontMetrics.descent) / 2f,
                textPaint
            )
            Log.d("measure","ascent:${fontMetrics.ascent}   descent:${fontMetrics.descent}")
        }
    }
}