package com.serdar.socket.data.dto.channel

data class Channel(
    val channel : String,
    val data : ChannelData,
    val event : String
){
    fun isTrade() : Boolean{
        return this.event == "trade"
    }
}
