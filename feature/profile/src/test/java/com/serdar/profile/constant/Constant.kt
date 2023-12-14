package com.serdar.profile.constant

import com.google.android.gms.common.util.VisibleForTesting
import com.serdar.profile.data.dto.CryptoPriceResponse
import com.serdar.profile.data.dto.Data

val apiException = Exception("Something went wrong")

@VisibleForTesting
val cryptoResponseDto = Data(
    changePercent24Hr = "1.3925869118620605",
    explorer = "https://blockchain.info/",
    id = "bitcoin",
    marketCapUsd = "830479950096.6031532883175476",
    maxSupply = "21000000.0000000000000000",
    name = "Bitcoin",
    rank = "1",
    supply = "19569693.0000000000000000",
    symbol = "BTC",
    volumeUsd24Hr = "11556024891.2337592935417282",
    vwap24Hr = "42588.2002449942167456",
    priceUsd = "42437"
)
val cryptoResponse = CryptoPriceResponse(
    data = listOf(cryptoResponseDto),
    timestamp = 100L
)

const val SAMPLE_RESPONSE_FILE = "CryptoPrice.json"
