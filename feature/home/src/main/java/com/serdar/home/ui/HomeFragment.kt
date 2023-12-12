package com.serdar.home.ui

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.serdar.common.base.BaseFragment
import com.serdar.chart.companent.CoinChartDataViewState
import com.serdar.home.companent.MockCoinDataProvider
import com.serdar.home.databinding.FragmentHomeBinding
import com.serdar.socket.data.SocketStateManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val viewModel by viewModels<HomeViewModel>()

    override fun observeUi() {
        super.observeUi()
        setSocketEvent()
        binding.chartView.updateCoinItems(CoinChartDataViewState(MockCoinDataProvider.provideMockCoinData(), isError = false, isDownloading = false, "TR"))

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
                            Log.e("TAG", "setSocketEvent: $it")
                        }

                        SocketStateManager.Connecting -> {
                            Log.e("TAG", "setSocketEvent: $it")

                        }

                        SocketStateManager.Disconnected -> {
                            Log.e("TAG", "setSocketEvent: $it")

                        }

                        SocketStateManager.Disconnecting -> {
                            Log.e("TAG", "setSocketEvent: $it")

                        }

                        is SocketStateManager.Error -> {
                            Log.e("TAG", "setSocketEvent: $it")

                        }

                        is SocketStateManager.Price -> {
                            Log.e("TAG", "setSocketEvent: $it")
                            if (it.channel=="live_trades_btcusd"){

                            }

                        }
                    }
                }
        }
    }
}