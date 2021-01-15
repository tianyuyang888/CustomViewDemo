package com.yangtianyu.customviewdemo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.math.cos
import kotlin.math.sin

class PieChartView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val RADIUS = 150f.dp
    private val OFFSET = 20f.dp
    private val ANGLES = floatArrayOf(60f, 80f, 120f, 100f)
    private val COLORS = listOf(
        Color.parseColor("#ff0000"),
        Color.parseColor("#ffff00"),
        Color.parseColor("#ff00ff"),
        Color.parseColor("#00ffff")
    )

    //2、创建绘制对象
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    //3、处理布局事件


    //1、重写onDraw()
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //4、绘制
        var startAngle = 0f
        canvas.apply {
            for ((index, angle) in ANGLES.withIndex()) {
                if (index == 2) {
                    canvas.save()
                    canvas.translate(
                        (OFFSET * cos(Math.toRadians((startAngle + (angle) / 2f).toDouble()))).toFloat(),
                        (OFFSET * sin(Math.toRadians((startAngle + (angle) / 2f).toDouble()))).toFloat()
                    )
                }
                paint.color = COLORS[index]
                drawArc(
                    width / 2f - RADIUS,
                    height / 2f - RADIUS,
                    width / 2f + RADIUS,
                    height / 2f + RADIUS,
                    startAngle, angle, true, paint
                )
                startAngle += angle
                if (index == 2)
                    canvas.restore()
            }

        }
    }
}