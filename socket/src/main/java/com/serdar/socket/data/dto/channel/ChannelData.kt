package com.serdar.socket.data.dto.channel

data class ChannelData(
    val id : Long,
    val buy_order_id : Long,
    val amount : Double,
    val amount_str : String,
    val price : Double,
    val price_str : String,
    val timestamp : String,
    val microtimestamp : String,
    val type : Int
)
