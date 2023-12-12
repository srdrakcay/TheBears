package com.serdar.home.companent

import kotlin.random.Random

object MockCoinDataProvider {

    fun provideMockCoinData(): List<CoinChartData> {
        return mutableListOf(
            CoinChartData("01:00", randomValueProvider()),
            CoinChartData("02:00", randomValueProvider()),
            CoinChartData("03:00", randomValueProvider()),
            CoinChartData("04:00", randomValueProvider()),
            CoinChartData("05:00", randomValueProvider()),
            CoinChartData("06:00", randomValueProvider()),
            CoinChartData("07:00", randomValueProvider()),
            CoinChartData("08:00", randomValueProvider()),
            CoinChartData("09:00", randomValueProvider()),
        )
    }

    private fun randomValueProvider(): Double {
        return Random.nextDouble(1000.00, 20000.00)
    }
}