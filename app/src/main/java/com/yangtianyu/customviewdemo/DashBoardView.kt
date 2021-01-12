package com.yangtianyu.customviewdemo

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.math.cos
import kotlin.math.sin


/**
 * 仪表盘
 *
 * 1、创建自定义视图类
 *      1、创建视图子类
 *      2、定义自定义属性
 *      3、应用自定义属性
 *      4、添加属性和事件
 *      5、无障碍设计
 * 2、实现自定义绘制
 *      1、重写onDraw()
 *      2、创建绘制对象
 *          1、canvas
 *          2、paint
 *      3、处理布局事件
 *          1、onSizeChange()
 *      4、绘制
 *          1、坐标系
 *          2、尺寸单位
 * 3、使视图可交互
 *      1、处理输入手势
 *      2、创建符合物理原理的运动
 *      3、实现切换流畅
 * 4、优化视图
 */
class DashBoardView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private var dashRadius: Float
    private var pointerLength: Float
    private var shapeWidth: Float
    private var shapeHeight: Float
    private var dashAngle: Int
    private var pointer: Int

    private val dashPath = Path()
    private val pathMeasure = PathMeasure()

    init {
        context!!.theme.obtainStyledAttributes(
            attrs,
            R.styleable.DashBoardView,
            0, 0
        ).apply {
            try {
                dashRadius = getFloat(R.styleable.DashBoardView_dashRadius, 120f).px
                pointerLength = getFloat(R.styleable.DashBoardView_pointerLength, 100f).px
                shapeWidth = getFloat(R.styleable.DashBoardView_shapeWidth, 2f).px
                shapeHeight = getFloat(R.styleable.DashBoardView_shapeHeight, 10f).px
                dashAngle = getInt(R.styleable.DashBoardView_dashAngle, 120)
                pointer = getInt(R.styleable.DashBoardView_pointer, 5)
            } finally {
                recycle()
            }
        }

    }

    private val shape = Path().apply {
        addRect(0f, 0f, shapeWidth, shapeHeight, Path.Direction.CCW)
    }

    private val dashPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 2f.px
    }

    private val scalePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {


    }

    private val pointerPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        strokeWidth = shapeWidth
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        dashPath.addArc(
            width / 2 - dashRadius,
            height / 2 - dashRadius,
            width / 2 + dashRadius,
            height / 2 + dashRadius,
            90 + dashAngle / 2f,
            360f - dashAngle
        )
        pathMeasure.setPath(dashPath, false)
        val pathDashPathEffect = PathDashPathEffect(
            shape,
            (pathMeasure.length - shapeWidth) / 20f,
            0f,
            PathDashPathEffect.Style.ROTATE
        )
        scalePaint.pathEffect = pathDashPathEffect
    }

    private fun getRadians(angle: Float) =
        Math.toRadians((angle).toDouble()).toFloat()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.apply {
            //1、画弧
            drawPath(dashPath, dashPaint)
            //2、画刻度
            drawPath(dashPath, scalePaint)

            //3、画指针
            drawLine(
                width / 2f, height / 2f,
                width / 2f + pointerLength * cos(getRadians(90 + dashAngle / 2 + (360 - dashAngle) / 20f * pointer)),
                height / 2f + pointerLength * sin(getRadians(90 + dashAngle / 2 + (360 - dashAngle) / 20f * pointer)),
                pointerPaint
            )

        }


    }


}