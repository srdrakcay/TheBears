package com.serdar.chart.companent

class CoinChartFormatter {
    fun format(input: Double): String {
        return BigNumberFormatter.formatDouble(input)
    }
}