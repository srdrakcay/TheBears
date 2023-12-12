package com.serdar.chart.companent

data class CoinChartDataViewState(
    val coins: List<CoinChartData>,
    val isError: Boolean,
    val isDownloading: Boolean,
    val currency: String
) {

    companion object {
        fun empty() = CoinChartDataViewState(
            emptyList(),
            isError = false,
            isDownloading = false,
            currency = ""
        )
    }
}