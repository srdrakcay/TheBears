package com.serdar.chart.companent

import com.serdar.chart.companent.BigNumberFormatter

class CoinChartFormatter{
    fun format(input: Double): String {
        return BigNumberFormatter.formatDouble(input)
    }
}