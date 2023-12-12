package com.serdar.home.companent

import android.graphics.RectF
import com.serdar.chart.companent.TextPosition

data class IndicatorTextData(
    val indicatorRectangle: RectF,
    val time: IndicatorText,
    val value: IndicatorText,
    val circlePosition: TextPosition
)