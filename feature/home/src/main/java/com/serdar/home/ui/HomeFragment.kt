package com.serdar.home.ui

import android.util.Log
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.serdar.chart.companent.CoinChartDataViewState
import com.serdar.chart.companent.MockCoinDataProvider
import com.serdar.common.base.BaseFragment
import com.serdar.common.extensions.combineString
import com.serdar.common.extensions.notShow
import com.serdar.common.extensions.show
import com.serdar.home.R
import com.serdar.home.databinding.FragmentHomeBinding
import com.serdar.socket.data.SocketStateManager
import com.serdar.socket.util.Difference
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import com.serdar.localization.R as localizationR

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val viewModel by viewModels<HomeViewModel>()

    companion object {
        private val dataList: ArrayList<Double> = arrayListOf()
        const val PRICE_CHANNEL_NAME = "live_trades_btcusd"
    }

    override fun observeUi() {
        super.observeUi()
        setSocketEvent()
        binding.chartView.notShow()

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
        val socketStatus = resources.getString(localizationR.string.socket_status)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect {
                    when (it) {
                        SocketStateManager.Connected -> {
                            Log.e("TAG", "setSocketEvent: Connected")
                            setSubscribeSocketChannelNames()
                            binding.txtCryptoBtcPrice.text =
                                socketStatus.combineString(it.toString())

                        }

                        SocketStateManager.Connecting -> {
                            Log.e("TAG", "setSocketEvent: Connecting")

                            binding.txtCryptoBtcPrice.text =
                                socketStatus.combineString(it.toString())

                        }

                        SocketStateManager.Disconnected -> {
                            Log.e("TAG", "setSocketEvent: Disconnected")

                            binding.txtCryptoBtcPrice.text =
                                socketStatus.combineString(it.toString())

                        }

                        SocketStateManager.Disconnecting -> {
                            Log.e("TAG", "setSocketEvent: Disconnecting")

                            binding.txtCryptoBtcPrice.text =
                                socketStatus.combineString(it.toString())

                        }

                        is SocketStateManager.Error -> {
                            Log.e("TAG", "setSocketEvent: Error")

                            setUnsubscribeSocketChannelNames()
                        }

                        is SocketStateManager.Price -> {
                            Log.e("TAG", "setSocketEvent: Price")

                            if (it.channel == PRICE_CHANNEL_NAME) {
                                setExchange(it.exchange)
                                binding.txtCryptoBtcPrice.text =
                                    resources.getString(com.serdar.localization.R.string.socket_btc)
                                        .combineString(it.value.toString())
                            }

                            handlePriceState(it)
                        }

                        else -> {}
                    }
                }
        }
    }

    private fun handlePriceState(socketEvent: SocketStateManager.Price) {
        if (socketEvent.channel == PRICE_CHANNEL_NAME) {
            dataList.add(socketEvent.value)
            if (dataList.size > 9) {
                binding.chartView.show()
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


    private fun setExchange(difference: Difference) {
        when (difference) {
            Difference.UP -> {
                binding.txtCryptoBtcPrice.setTextColor(
                    AppCompatResources.getColorStateList(
                        requireContext(), R.color.percentageUp
                    )
                )
            }

            Difference.Down -> {
                binding.txtCryptoBtcPrice.setTextColor(
                    AppCompatResources.getColorStateList(
                        requireContext(), R.color.percentageDown
                    )
                )
            }

            Difference.Stable -> {
                binding.txtCryptoBtcPrice.setTextColor(
                    AppCompatResources.getColorStateList(
                        requireContext(), R.color.black
                    )
                )
            }
        }
    }
}