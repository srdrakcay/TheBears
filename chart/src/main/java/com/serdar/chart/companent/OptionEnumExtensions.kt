package com.serdar.chart.companent

import android.content.Context
import com.serdar.chart.R

private const val ONE_DAY_IN_MILLISECONDS = 86400000L
private const val ONE_WEEK_IN_MILLISECONDS = ONE_DAY_IN_MILLISECONDS * 7
private const val ONE_MONTH_IN_MILLISECONDS = ONE_DAY_IN_MILLISECONDS * 30
private const val SIX_MONTH_IN_MILLISECOND = ONE_MONTH_IN_MILLISECONDS * 6
private const val ONE_YEAR_IN_MILLISECONDS = ONE_DAY_IN_MILLISECONDS * 365

fun OptionEnum.getName(context: Context): String {
    return when (this) {
        OptionEnum.ONE_DAY -> context.getString(R.string.assets_detail_chart_interval_1d)
        OptionEnum.ONE_WEEK -> context.getString(R.string.assets_detail_chart_interval_1w)
        OptionEnum.ONE_MONTH -> context.getString(R.string.assets_detail_chart_interval_1m)
        OptionEnum.SIX_MONTH -> context.getString(R.string.assets_detail_chart_interval_6m)
        OptionEnum.ONE_YEAR -> context.getString(R.string.assets_detail_chart_interval_1y)
        OptionEnum.ALL_TIME -> context.getString(R.string.assets_detail_chart_interval_alltime)
    }
}

fun OptionEnum.getInterval(): String {
    return when (this) {
        OptionEnum.ONE_DAY -> "5m"
        OptionEnum.ONE_WEEK -> "15m"
        OptionEnum.ONE_MONTH -> "1h"
        OptionEnum.SIX_MONTH -> "1d"
        OptionEnum.ONE_YEAR -> "1d"
        OptionEnum.ALL_TIME -> "7d"
    }
}

fun OptionEnum.getStartTime(now: Long): Long {
    return when (this) {
        OptionEnum.ONE_DAY -> (now - ONE_DAY_IN_MILLISECONDS)
        OptionEnum.ONE_WEEK -> (now - ONE_WEEK_IN_MILLISECONDS)
        OptionEnum.ONE_MONTH -> (now - ONE_MONTH_IN_MILLISECONDS)
        OptionEnum.SIX_MONTH -> (now - SIX_MONTH_IN_MILLISECOND)
        OptionEnum.ONE_YEAR -> (now - ONE_YEAR_IN_MILLISECONDS)
        OptionEnum.ALL_TIME -> 1L
    }
}

fun OptionEnum.getLimit(): Int {
    return when (this) {
        OptionEnum.ONE_DAY -> 5 * 12 * 24
        OptionEnum.ONE_WEEK -> 15 * 4 * 24 * 7
        OptionEnum.ONE_MONTH -> 1 * 24 * 30
        OptionEnum.SIX_MONTH -> 1 * 24 * 30 * 6
        OptionEnum.ONE_YEAR -> 1 * 24 * 365
        OptionEnum.ALL_TIME -> 1 * 24 * 365
    }
}