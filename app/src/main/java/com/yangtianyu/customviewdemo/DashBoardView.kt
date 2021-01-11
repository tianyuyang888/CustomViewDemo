package com.yangtianyu.customviewdemo

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.math.cos
import kotlin.math.sin

private val SHAPE_WIDTH = 2f.dp
private val SHAPE_LENGTH = 10f.dp
private val POINT_LENGTH = 100f.dp

class DashBoardView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val dashAngle = 120f
    private val dashRadius = 150f.dp
    private val shape = Path()
    private val dashPath = Path()
    private val pathMeasure = PathMeasure()
    private val point = 5


    //2、创建绘制对象
    private val dashPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 2f.dp
    }

    private val scalePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 10f.dp
        shape.addRect(0f, 0f, SHAPE_WIDTH, SHAPE_LENGTH, Path.Direction.CCW)

    }

    private val pointerPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        strokeWidth = 2f.dp
    }

    //1、替换onDraw()
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //4、绘制
        canvas.apply {
            //1、画圆弧
            drawPath(
                dashPath,
                dashPaint
            )
            //2、画刻度
            drawPath(
                dashPath,
                scalePaint
            )
            //3、画指针
            drawLine(
                width / 2f,
                height / 2f,
                (width / 2f + POINT_LENGTH * cos(Math.toRadians((90 + dashAngle / 2f + (360 - dashAngle) / 20f * point).toDouble()))).toFloat(),
                (height / 2f + POINT_LENGTH * sin(Math.toRadians((90 + dashAngle / 2f + (360 - dashAngle) / 20f * point).toDouble()))).toFloat(),
                pointerPaint
            )
        }

    }

    //3、处理布局事件
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        dashPath.addArc(
            width / 2f - dashRadius,
            height / 2f - dashRadius,
            width / 2f + dashRadius,
            height / 2f + dashRadius,
            90 + dashAngle / 2,
            360 - dashAngle
        )
        pathMeasure.setPath(dashPath, false)
        scalePaint.pathEffect =
            PathDashPathEffect(
                shape,
                (pathMeasure.length - SHAPE_WIDTH) / 20f,
                0f,
                PathDashPathEffect.Style.ROTATE
            )
    }


}