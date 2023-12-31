package com.serdar.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.serdar.socket.data.dto.subscription.SubscriptionSocket
import com.serdar.socket.data.dto.subscription.SubscriptionSocketData
import com.serdar.socket.socketnetwork.SocketManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val socketManager: SocketManager,
    private val gson: Gson,

) : ViewModel() {
    val state = socketManager.events.shareIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(5000)
    )

    fun subscribeSocket(channelsName: List<String>) {

        val subscribeMessages = channelsName.map { channelName ->
            SubscriptionSocket("bts:subscribe", SubscriptionSocketData(channel = channelName))
        }
        subscribeMessages.forEach { subscribeMessage ->

            socketManager.socket.send(gson.toJson(subscribeMessage))
        }
    }

    fun unsubscribeSocket(channelsName: List<String>) {
        val unsubscribeMessages = channelsName.map { channelName ->
            SubscriptionSocket("bts:unsubscribe", SubscriptionSocketData(channel = channelName))
        }
        unsubscribeMessages.forEach { subscribeMessage ->
            socketManager.socket.send(gson.toJson(subscribeMessage))
        }
    }



}