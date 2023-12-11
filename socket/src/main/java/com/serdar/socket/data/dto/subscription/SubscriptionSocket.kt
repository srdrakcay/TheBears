package com.serdar.socket.data.dto.subscription

data class SubscriptionSocket(
    val event : String,
    val data : SubscriptionSocketData = SubscriptionSocketData()
)