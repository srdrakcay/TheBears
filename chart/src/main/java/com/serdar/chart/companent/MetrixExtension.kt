package com.serdar.home.companent

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue

fun Int.dp(context: Context): Int {
    val scale = context.resources.displayMetrics.density
    return (this * scale + 0.5f).toInt()
}

fun Int.sp(context: Context): Int {
    val scale = context.resources.displayMetrics.scaledDensity
    return (this * scale + 0.5f).toInt()
}

val Number.px: Int
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    ).toInt()