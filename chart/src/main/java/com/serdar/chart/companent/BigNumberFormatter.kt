package com.serdar.chart.companent

import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.absoluteValue

object BigNumberFormatter {

    fun formatBuySellValues(number: Double): String {
        val buySellFormat: NumberFormat = NumberFormat.getInstance(Locale.getDefault()).apply {
            maximumFractionDigits = 12
            maximumIntegerDigits = 12
        }
        return buySellFormat.format(number)
    }

    fun formatDouble(number: Double): String = getFormatter(number).format(number)

    fun formatPrice(number: String): String {
        val assetFormat: NumberFormat = NumberFormat.getInstance(Locale.getDefault()).apply {
            maximumFractionDigits = 6
        }
        val parsedNumber = assetFormat.parse(number)!!.toDouble()
        return getFormatter(parsedNumber).format(parsedNumber)
    }

    fun formatNumber(number: String): String {
        val assetFormat: NumberFormat = NumberFormat.getInstance(Locale.getDefault()).apply {
            maximumFractionDigits = 6
        }
        val parsedNumber = assetFormat.parse(number)!!.toDouble()
        return getAssetFormatter(assetFormat, parsedNumber).format(parsedNumber)
    }

    fun formatNumberToDouble(number: String): Double {
        val assetFormat: NumberFormat = NumberFormat.getInstance(Locale.getDefault()).apply {
            maximumFractionDigits = 6
            roundingMode = RoundingMode.DOWN
        }
        return assetFormat.parse(number)!!.toDouble()
    }


    fun formatPercentage(percentage: Float, fractionDigits: Int): String {
        val decimalFormat = NumberFormat.getPercentInstance(Locale.getDefault()).apply {
            minimumFractionDigits = fractionDigits
        }
        return decimalFormat.format(percentage / 100)
    }

    private fun getFormatter(number: Double): NumberFormat {
        val format: NumberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault())
        val decimalFormatSymbols = (format as DecimalFormat).decimalFormatSymbols
        decimalFormatSymbols.currencySymbol = ""
        format.decimalFormatSymbols = decimalFormatSymbols
        if (number.absoluteValue < 1) {
            format.maximumFractionDigits = 6
        } else {
            format.roundingMode = RoundingMode.DOWN
            format.maximumFractionDigits = 2
        }
        return format
    }

    private fun getAssetFormatter(assetFormat: NumberFormat, number: Double): NumberFormat {
        assetFormat.roundingMode = RoundingMode.DOWN
        assetFormat.maximumFractionDigits = if (number.absoluteValue < 1) {
            6
        } else {
            2
        }
        return assetFormat
    }
}