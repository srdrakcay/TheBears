package com.serdar.home.companent

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.graphics.RectF
import android.text.TextPaint
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import com.serdar.chart.R
import com.serdar.chart.companent.CoinChartDataViewState
import com.serdar.chart.companent.CoinChartFormatter
import com.serdar.chart.companent.CoinPointData
import com.serdar.chart.companent.TextPosition
import kotlin.math.abs
import kotlin.math.max

class CoinTradeView @JvmOverloads constructor(
    context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attributeSet, defStyleAttr) {

    private val maxTextBottomMargin = 16.px.toFloat()

    private val triangleVertexLength = 8.px.toFloat()

    private val chartVerticalMargin = 16.px.toFloat()

    private val triangleBottomMargin = 8.px.toFloat()

    private val minTextBottomMargin = 2.px.toFloat()

    private val indicatorRectangleVerticalBoundMargin = 16.px.toFloat()

    private val chartBottomMarginToTriangle = 8.px.toFloat()

    private val textMargin = 8.px.toFloat()

    private val outerCircleDifference = 2.px.toFloat()

    private val innerCircleRadius = textMargin / 2

    private val outerCircleRadius = innerCircleRadius + outerCircleDifference

    private var isValueIndicatorVisible = false

    private val trianglePath = Path()
    private val dotPointDashPath = Path()

    private var indicatorTextData: IndicatorTextData? = null

    private var minText: IndicatorText? = null
    private var maxText: IndicatorText? = null

    private val minTextBound = Rect()
    private val maxTextBound = Rect()
    private var currency = "$"

    private val coinChartFormatter = CoinChartFormatter()

    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.tabIndicator)
        style = Paint.Style.STROKE
        strokeWidth = 2.px.toFloat()
        strokeCap = Paint.Cap.ROUND
    }

    private val indicatorRectanglePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.tabIndicator)
        style = Paint.Style.FILL_AND_STROKE
        strokeCap = Paint.Cap.ROUND
    }

    private val innerCirclePoint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.tabIndicator)
        style = Paint.Style.FILL_AND_STROKE
        strokeCap = Paint.Cap.ROUND
    }

    private val outerCirclePoint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, android.R.color.white)
        style = Paint.Style.FILL_AND_STROKE
        strokeCap = Paint.Cap.ROUND
    }

    private val valueIndicatorLinePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.unselectedTabTextColor)
        style = Paint.Style.STROKE
        strokeWidth = 2.px.toFloat()
        pathEffect = DashPathEffect(floatArrayOf(10f, 10f), 1f)
    }

    private val trianglePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.unselectedTabTextColor)
        style = Paint.Style.FILL_AND_STROKE
        strokeWidth = 2.px.toFloat()
    }

    private val timeTextPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 12.sp(context).toFloat()
        color = ContextCompat.getColor(context, R.color.inActiveButtonBlue)
        style = Paint.Style.FILL_AND_STROKE
    }

    private val minMaxTextPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 12.sp(context).toFloat()
        color = ContextCompat.getColor(context, R.color.textGray)
        style = Paint.Style.FILL_AND_STROKE
    }

    private val valueTextPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 12.sp(context).toFloat()
        color = ContextCompat.getColor(context, android.R.color.white)
        style = Paint.Style.FILL_AND_STROKE
    }

    private val gestureDetector =
        GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onLongPress(e: MotionEvent) {
                super.onLongPress(e)
                handleLongPress(e)
            }
        })

    private val viewRectF = RectF()

    private val coinPoints = mutableListOf<CoinPointData>()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        viewRectF.set(0f, 0f, w.toFloat(), h.toFloat())
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (coinPoints.isNotEmpty()) {
            canvas.drawLine(
                0f,
                coinPoints[0].y,
                coinPoints[0].x,
                coinPoints[0].y,
                linePaint
            )
        }

        for (i in 0 until coinPoints.lastIndex) {
            canvas.drawLine(
                coinPoints[i].x,
                coinPoints[i].y,
                coinPoints[i + 1].x,
                coinPoints[i + 1].y,
                linePaint
            )
        }

        if (coinPoints.isNotEmpty()) {
            canvas.drawLine(
                coinPoints[coinPoints.lastIndex].x,
                coinPoints[coinPoints.lastIndex].y,
                viewRectF.right,
                coinPoints[coinPoints.lastIndex].y,
                linePaint
            )
        }

        minText?.let { indicatorText ->
            with(indicatorText) {
                canvas.drawText(text, position.x, position.y, minMaxTextPaint)
            }
        }

        maxText?.let { indicatorText ->
            with(indicatorText) {
                canvas.drawText(text, position.x, position.y, minMaxTextPaint)
            }
        }


        if (isValueIndicatorVisible) {
            indicatorTextData?.let {

                canvas.drawPath(dotPointDashPath, valueIndicatorLinePaint)
                canvas.drawPath(trianglePath, trianglePaint)

                with(it.circlePosition) {
                    canvas.drawCircle(x, y, outerCircleRadius, outerCirclePoint)
                    canvas.drawCircle(x, y, innerCircleRadius, innerCirclePoint)
                }

                canvas.drawRoundRect(
                    it.indicatorRectangle,
                    4.px.toFloat(),
                    4.px.toFloat(),
                    indicatorRectanglePaint
                )

                with(it.time) {
                    canvas.drawText(text, position.x, position.y, timeTextPaint)
                }

                with(it.value) {
                    canvas.drawText(text, position.x, position.y, valueTextPaint)
                }
            }
        }
    }

    fun updateCoinItems(chartDataViewState: CoinChartDataViewState) {
        if (chartDataViewState.coins.isEmpty()) return
        currency = chartDataViewState.currency
        coinPoints.clear()

        val pointMargin =
            (viewRectF.width() - 2 * chartVerticalMargin) / (chartDataViewState.coins.size - 1)

        val minPriceCoin = chartDataViewState.coins.minByOrNull { it.value }!!

        val maxPriceCoin = chartDataViewState.coins.maxByOrNull { it.value }!!

        val minValue = coinChartFormatter.format(minPriceCoin.value) + chartDataViewState.currency
        minMaxTextPaint.getTextBounds(minValue, 0, minValue.length, minTextBound)

        val maxValue = coinChartFormatter.format(maxPriceCoin.value) + chartDataViewState.currency
        minMaxTextPaint.getTextBounds(maxValue, 0, maxValue.length, maxTextBound)

        val chartBottomY =
            viewRectF.bottom - triangleVertexLength - 2 * minTextBound.height() - chartBottomMarginToTriangle - minTextBottomMargin
        val chartTopY = viewRectF.top + maxTextBound.height() + maxTextBottomMargin

        val chartHeight = chartBottomY - chartTopY

        var currentX = chartVerticalMargin

        val allDistance = maxPriceCoin.value - minPriceCoin.value

        chartDataViewState.coins.forEach {
            val valueTextRectBound = Rect()

            val value = coinChartFormatter.format(it.value) + chartDataViewState.currency
            minMaxTextPaint.getTextBounds(value, 0, value.length, valueTextRectBound)

            val percentage = ((it.value - minPriceCoin.value) / allDistance).toFloat()
            val y = chartBottomY - percentage * chartHeight

            when (it.value) {
                minPriceCoin.value -> {
                    val position = TextPosition(
                        x = getXForMinMaxText(currentX, valueTextRectBound.width().toFloat()),
                        y = viewRectF.bottom - minTextBottomMargin
                    )
                    minText = IndicatorText(minValue, position)
                }

                maxPriceCoin.value -> {
                    val position = TextPosition(
                        x = getXForMinMaxText(currentX, valueTextRectBound.width().toFloat()),
                        y = viewRectF.top + maxTextBound.height()
                    )
                    maxText = IndicatorText(maxValue, position)
                }

                else -> Unit
            }
            coinPoints.add(CoinPointData(it, x = currentX, y = y))
            currentX += pointMargin
        }
        invalidate()
    }

    private fun getXForMinMaxText(currentX: Float, textWidth: Float): Float {
        return if ((currentX - textWidth / 2f) < viewRectF.left) {
            viewRectF.left
        } else if (currentX + textWidth / 2f > viewRectF.right) {
            viewRectF.right - textWidth
        } else {
            currentX - textWidth / 2f
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        gestureDetector.onTouchEvent(event)
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                handleActionDownEvent()
            }

            MotionEvent.ACTION_MOVE -> {
                handleMoveEvent(event)
            }
        }
        return true
    }

    private fun handleActionDownEvent() {
        if (isValueIndicatorVisible) {
            isValueIndicatorVisible = false
            invalidate()
        }
    }

    private fun handleMoveEvent(event: MotionEvent) {
        if (isValueIndicatorVisible) {
            findIndicatorValues(event)
        }
    }

    private fun findIndicatorValues(event: MotionEvent) {
        val coinPointData = findCoinPointData(event)
        val time = coinPointData.coinChartData.time
        val value = coinChartFormatter.format(coinPointData.coinChartData.value) + currency

        val indicatorRectangle = RectF()

        val valueTextRectFBound = Rect()
        val timeTextRectBound = Rect()

        timeTextPaint.getTextBounds(time, 0, time.length, timeTextRectBound)
        timeTextPaint.getTextBounds(value, 0, value.length, valueTextRectFBound)

        val minWidthOfIndicatorRectangle =
            max(timeTextRectBound.width(), valueTextRectFBound.width())

        val circlePosition = TextPosition(coinPointData.x, coinPointData.y)

        val heightOfIndicatorRectangle =
            valueTextRectFBound.height() + timeTextRectBound.height() + 3 * textMargin

        val indicatorRectangleBottom =
            if (circlePosition.y - heightOfIndicatorRectangle - textMargin - outerCircleRadius < viewRectF.top) {
                circlePosition.y + textMargin + heightOfIndicatorRectangle + outerCircleRadius
            } else {
                circlePosition.y - textMargin - outerCircleRadius
            }

        val (left, right) = if (circlePosition.x - minWidthOfIndicatorRectangle / 2 - textMargin < viewRectF.left) {
            val left = viewRectF.left + indicatorRectangleVerticalBoundMargin
            val right = left + minWidthOfIndicatorRectangle + 2 * textMargin
            left to right
        } else if (circlePosition.x + minWidthOfIndicatorRectangle / 2 + textMargin > viewRectF.right) {
            val right = viewRectF.right - indicatorRectangleVerticalBoundMargin
            val left = right - minWidthOfIndicatorRectangle - 2 * textMargin
            left to right
        } else {
            val left = coinPointData.x - minWidthOfIndicatorRectangle / 2 - textMargin
            val right = coinPointData.x + minWidthOfIndicatorRectangle / 2 + textMargin
            left to right
        }

        indicatorRectangle.set(
            left,
            indicatorRectangleBottom - heightOfIndicatorRectangle,
            right,
            indicatorRectangleBottom
        )

        val timePosition = TextPosition(
            indicatorRectangle.left + textMargin,
            indicatorRectangle.top + textMargin + timeTextRectBound.height()
        )

        val timeText = IndicatorText(time, timePosition)

        val valuePosition = TextPosition(timePosition.x, indicatorRectangle.bottom - textMargin)

        val valueText = IndicatorText(value, valuePosition)

        indicatorTextData =
            IndicatorTextData(indicatorRectangle, timeText, valueText, circlePosition)

        val bottom =
            viewRectF.bottom - minTextBound.height() - triangleBottomMargin - minTextBottomMargin

        trianglePath.reset()
        trianglePath.moveTo(coinPointData.x, bottom)
        trianglePath.lineTo(coinPointData.x - triangleVertexLength / 2, bottom)
        trianglePath.lineTo(coinPointData.x, bottom - triangleVertexLength)
        trianglePath.lineTo(coinPointData.x + triangleVertexLength / 2, bottom)
        trianglePath.close()

        dotPointDashPath.reset()
        dotPointDashPath.moveTo(coinPointData.x, bottom)
        dotPointDashPath.lineTo(coinPointData.x, coinPointData.y)
        invalidate()
    }

    private fun handleLongPress(event: MotionEvent) {
        isValueIndicatorVisible = true
        findIndicatorValues(event)
    }

    private fun findCoinPointData(event: MotionEvent): CoinPointData {
        var closestIndex = 0
        var closestDistance = abs(event.x - coinPoints[closestIndex].x)

        for (i in 1 until coinPoints.size) {
            val distance = abs(event.x - coinPoints[i].x)
            if (distance < closestDistance) {
                closestIndex = i
                closestDistance = distance
            }
        }
        return coinPoints[closestIndex]
    }

    fun intervalChanged() {
        isValueIndicatorVisible = false
        indicatorTextData = null
        invalidate()
    }
}