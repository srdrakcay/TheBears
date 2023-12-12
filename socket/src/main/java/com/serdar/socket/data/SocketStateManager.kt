package com.serdar.socket.data

import com.serdar.socket.data.dto.channel.ChannelData
import com.serdar.socket.util.Difference

sealed class SocketStateManager{
    data object Connecting : SocketStateManager()
    data object Connected : SocketStateManager()
    data class Price(val value : Double, val  exchange : Difference, val exchangeValue : Double,val channel:String) : SocketStateManager()
    data object Disconnecting : SocketStateManager()
    data object Disconnected : SocketStateManager()
    data class Error(val error : String) : SocketStateManager()

}
