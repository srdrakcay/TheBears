package com.serdar.home.ui

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.serdar.common.base.BaseFragment
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
    }

    private fun setSubscribeSocketChannelNames() {
        val channelNames = listOf(
            "btcusd",
            "btceur",
            "btcgbp",
            "btcpax",
            "gbpusd",
            "eurusd",
            "xrpusd",
            "xrpeur",
            "xrpbtc",
        )
        viewModel.subscribeSocket(channelNames)
    }

    private fun setUnsubscribeSocketChannelNames() {
        val channelNames = listOf(
            "btcusd",
            "btceur",
            "btcgbp",
            "btcpax",
            "gbpusd",
            "eurusd",
            "xrpusd",
            "xrpeur",
            "xrpbtc",
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
                            setUnsubscribeSocketChannelNames()
                        }

                        SocketStateManager.Disconnecting -> {
                            setUnsubscribeSocketChannelNames()
                        }

                        is SocketStateManager.Error -> {

                        }

                        is SocketStateManager.Price -> {
                        }
                    }
                }
        }
    }
}