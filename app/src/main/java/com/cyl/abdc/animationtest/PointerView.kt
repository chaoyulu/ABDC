package com.cyl.abdc.animationtest

import android.animation.Animator
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.min

class PointerView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {
    private val path by lazy { Path() }
    private val circlePaint by lazy { Paint() }
    private val pointerPaint by lazy { Paint() }
    private var pointerCircleRadius = 0f // 指针中间的圆的半径
    private var pointerPadding = 0f
    private var circleWidth = 0f
    private var degree = 0f
    private var isAnimating = false
    private var circleColor = Color.BLACK
    private var pointerColor = Color.WHITE

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.translate((width) / 2f, (height) / 2f)
        drawView(canvas)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        circleWidth = min(w, h) / 2f // 中间圆形的直径为宽高最小边的一半
        pointerPadding = circleWidth / 10f // padding为大圆的1/10
        pointerCircleRadius = pointerPadding / 2f // 小圆半径为padding的一半
    }

    private fun drawView(canvas: Canvas) {
        circlePaint.color = circleColor
        pointerPaint.color = pointerColor
        canvas.drawColor(pointerColor)
        drawBigCircle(canvas, circlePaint)
        drawPointer(canvas, pointerPaint)
        drawSmallCircle(canvas, circlePaint)
    }

    // 画大圆
    private fun drawBigCircle(canvas: Canvas, paint: Paint) {
        canvas.drawCircle(0f, 0f, circleWidth / 2f, paint)
    }

    // 画小圆
    private fun drawSmallCircle(canvas: Canvas, paint: Paint) {
        canvas.drawCircle(0f, 0f, pointerCircleRadius, paint)
    }

    // 画指针
    private fun drawPointer(canvas: Canvas, paint: Paint) {
        path.reset()
        path.moveTo(0f, -(circleWidth / 2f - pointerPadding))
        path.lineTo(pointerCircleRadius * 3, 0f)
        path.lineTo(0f, circleWidth / 2f - pointerPadding)
        path.lineTo(-pointerCircleRadius * 3, 0f)
        path.close()

        canvas.rotate(degree, 0f, 0f)
        canvas.drawPath(path, paint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                performClick()
            }

            MotionEvent.ACTION_UP -> {
                if (isAnimating) return true
                degreeAnimation()
                // 指针颜色渐变
                colorAnimation(
                    startColor = if (degree == 0f) Color.WHITE else Color.BLACK,
                    endColor = if (degree == 0f) Color.BLACK else Color.WHITE
                ) { pointerColor = it.animatedValue as Int }

                // 背景颜色渐变
                colorAnimation(
                    startColor = if (degree == 0f) Color.BLACK else Color.WHITE,
                    endColor = if (degree == 0f) Color.WHITE else Color.BLACK
                ) { circleColor = it.animatedValue as Int }
            }
        }
        return true
    }

    private fun degreeAnimation() {
        val animation = ValueAnimator.ofFloat(degree, if (degree == 0f) 45f else 0f)
        animation.duration = 500
        animation.addUpdateListener { anim ->
            val value = anim.animatedValue as Float
            degree = value
            invalidate()
        }

        animation.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                isAnimating = true
            }

            override fun onAnimationEnd(animation: Animator) {
                isAnimating = false
            }

            override fun onAnimationCancel(animation: Animator) {
                isAnimating = false
            }

            override fun onAnimationRepeat(animation: Animator) {
            }
        })
        animation.start()
    }

    private fun colorAnimation(startColor: Int, endColor: Int, valueUpdated: (ValueAnimator) -> Unit) {
        val animation = ValueAnimator.ofObject(ArgbEvaluator(), startColor, endColor)
        animation.duration = 500
        animation.addUpdateListener { anim ->
            valueUpdated(anim)
        }
        animation.start()
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }
}