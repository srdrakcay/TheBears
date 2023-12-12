package com.serdar.home.data.dto

import com.squareup.moshi.Json

data class CryptoPriceResponse(
    @Json(name = "data")
    val `data`: List<Data>,
    @Json(name = "timestamp")
    val timestamp: Long
)