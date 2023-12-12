package com.serdar.home.companent

import android.graphics.RectF

data class IndicatorTextData(
    val indicatorRectangle: RectF,
    val time: IndicatorText,
    val value: IndicatorText,
    val circlePosition: TextPosition
)