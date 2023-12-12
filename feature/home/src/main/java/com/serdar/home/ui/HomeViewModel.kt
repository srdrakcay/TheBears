package com.serdar.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.serdar.home.data.NetworkResponseState
import com.serdar.home.domain.GetAllCryptoPriceUseCase
import com.serdar.socket.data.dto.subscription.SubscriptionSocket
import com.serdar.socket.data.dto.subscription.SubscriptionSocketData
import com.serdar.socket.socketnetwork.SocketManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val socketManager: SocketManager,
    private val gson: Gson,
    private val getAllCryptoPriceUseCase: GetAllCryptoPriceUseCase

) : ViewModel() {
    val state = socketManager.events
    private val _homeUiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val homeUiState: MutableStateFlow<HomeUiState> = _homeUiState
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

    fun getAllCryptoDataFromRest() {
        viewModelScope.launch {
            getAllCryptoPriceUseCase().onEach {
                when (it) {
                    is NetworkResponseState.Error -> {
                        _homeUiState.value = HomeUiState.Error(it.exception)
                    }

                    NetworkResponseState.Loading -> {
                        _homeUiState.value = (HomeUiState.Loading)
                    }

                    is NetworkResponseState.Success -> {
                        _homeUiState.value = (HomeUiState.Success(it.result!!))
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

}