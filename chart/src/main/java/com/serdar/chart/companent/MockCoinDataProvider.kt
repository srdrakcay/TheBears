package com.serdar.chart.companent

import kotlin.random.Random

object MockCoinDataProvider {

    fun provideMockCoinData(data:MutableList<Double>): List<CoinChartData> {
        return mutableListOf(
            CoinChartData("01:00", data.first()),
            CoinChartData("02:00", data[1]),
            CoinChartData("03:00", data[2]),
            CoinChartData("04:00", data[3]),
            CoinChartData("05:00", data[4]),
            CoinChartData("06:00", data[5]),
            CoinChartData("07:00", data[6]),
            CoinChartData("08:00", data[7]),
            CoinChartData("09:00", data.last()),
        )
    }

    private fun randomValueProvider(): Double {
        return Random.nextDouble(1000.00, 20000.00)
    }
}