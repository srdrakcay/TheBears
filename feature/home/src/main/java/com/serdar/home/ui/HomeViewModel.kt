package com.serdar.home.ui

import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.serdar.socket.data.dto.subscription.SubscriptionSocket
import com.serdar.socket.socketnetwork.SocketManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeViewModel @Inject constructor(
    private val socketManager: SocketManager,
    private val gson: Gson
) : ViewModel() {
    val state = socketManager.events

     fun subscribeSocket(channelName:List<String>) {
        socketManager.socket.send(gson.toJson(SUBSCRIBE_MESSAGE))
    }

    private fun unsubscribeSocket() {
        socketManager.socket.send(gson.toJson(UNSUBSCRIBE_MESSAGE))
    }

    companion object {
        private val SUBSCRIBE_MESSAGE = SubscriptionSocket(event = "bts:subscribe")
        private val UNSUBSCRIBE_MESSAGE = SubscriptionSocket(event = "bts:unsubscribe")
    }
}