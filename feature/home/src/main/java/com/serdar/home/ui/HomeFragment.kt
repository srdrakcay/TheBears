package com.serdar.home.ui

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.serdar.chart.companent.CoinChartDataViewState
import com.serdar.chart.companent.MockCoinDataProvider
import com.serdar.common.base.BaseFragment
import com.serdar.home.data.NetworkResponseState
import com.serdar.home.databinding.FragmentHomeBinding
import com.serdar.socket.data.SocketStateManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.math.log

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val viewModel by viewModels<HomeViewModel>()

    companion object {
        private val dataList: ArrayList<Double> = arrayListOf()
    }

    override fun callInitialViewModelFunctions() {
        super.callInitialViewModelFunctions()
        viewModel.getAllCryptoDataFromRest()
    }

    override fun observeUi() {
        super.observeUi()
        setSocketEvent()
        initObserve()
    }

    private fun setSubscribeSocketChannelNames() {
        val channelNames = listOf(
            "live_trades_btcusd",
            "live_trades_btceur",
            "live_trades_btcgbp",
            "live_trades_btcpax",
            "live_trades_gbpusd",
            "live_trades_eurusd",
            "live_trades_xrpusd",
            "live_trades_xrpeur",
            "live_trades_xrpbtc",
        )
        viewModel.subscribeSocket(channelNames)
    }

    private fun setUnsubscribeSocketChannelNames() {
        val channelNames = listOf(
            "live_trades_btcusd",
            "live_trades_btceur",
            "live_trades_btcgbp",
            "live_trades_btcpax",
            "live_trades_gbpusd",
            "live_trades_eurusd",
            "live_trades_xrpusd",
            "live_trades_xrpeur",
            "live_trades_xrpbtc",
        )
        viewModel.unsubscribeSocket(channelNames)
    }

    private fun setSocketEvent() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect {
                    when (it) {
                        SocketStateManager.Connected -> {
                            setSubscribeSocketChannelNames()
                        }

                        SocketStateManager.Connecting -> {
                        }

                        SocketStateManager.Disconnected -> {
                        }

                        SocketStateManager.Disconnecting -> {
                        }

                        is SocketStateManager.Error -> {
                        }

                        is SocketStateManager.Price -> {
                            handlePriceState(it)

                        }

                        else -> {}
                    }
                }
        }
    }

    private fun handlePriceState(socketEvent: SocketStateManager.Price) {
        if (socketEvent.channel == "live_trades_btcusd") {
            dataList.add(socketEvent.value)
            if (dataList.size > 9) {
                binding.chartView.updateCoinItems(
                    CoinChartDataViewState(
                        MockCoinDataProvider.provideMockCoinData(dataList),
                        isError = false,
                        isDownloading = false,
                        "$"
                    )
                )
            }
        }
    }

    private fun initObserve() {
        viewModel.viewModelScope.launch {
            viewModel.homeUiState.collect {
                when (it) {
                    is HomeUiState.Error -> {
                    }

                    HomeUiState.Loading -> {
                    }

                    is HomeUiState.Success -> {
                        Log.e("TAG", "initObserve:${it.data.data} ", )
                    }
                }
            }

        }
    }
}