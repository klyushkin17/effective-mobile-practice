package com.example.androidsdk.views

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.min
import kotlin.random.Random


class CustomRectangleView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var fillColor = getRandomColor()
        set(value) {
            filledPaint.color = value
            field = fillColor
        }
    private val filledPaint = Paint().apply {
        color = fillColor
        style = Paint.Style.FILL
    }
    private val strokePaint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 20f
    }

    private val outlinedRect = RectF()
    private val filledRect = RectF()

    private var heightPercentage = 0
        set(value) {
            filledRectangleHeight = height - (height * value / 100)
            field = value
            fillColor = getRandomColor()
            invalidate()
            requestLayout()
        }
    private var filledRectangleHeight = height

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredWidth = 140
        val desiredHeight = 100
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val width = when (widthMode) {
            MeasureSpec.EXACTLY -> widthSize
            MeasureSpec.AT_MOST -> min(desiredWidth, widthSize)
            else -> desiredWidth
        }

        val height = when (heightMode) {
            MeasureSpec.EXACTLY -> heightSize
            MeasureSpec.AT_MOST -> min(desiredHeight, heightSize)
            else -> desiredHeight
        }

        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        outlinedRect.set(0f, 0f, width.toFloat(), height.toFloat())
        filledRect.set(0f, filledRectangleHeight.toFloat(), width.toFloat(), height.toFloat())
        /*outlinedRect.set(width.toFloat(), height.toFloat(), 0f, 0f)
        filledRect.set(0f, width.toFloat(), filledRectangleHeight.toFloat(), 0f)*/
        canvas.drawRect(filledRect, filledPaint)
        canvas.drawRect(outlinedRect, strokePaint)
        super.onDraw(canvas)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                onRectangleClickHandle()
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    private fun getRandomColor(): Int {
        return -0x1000000 or (Random.nextInt(0xFFFFFF))
    }

    private fun onRectangleClickHandle() {
        if (heightPercentage < 100) {
            heightPercentage += 10
        } else heightPercentage = 0
    }
}