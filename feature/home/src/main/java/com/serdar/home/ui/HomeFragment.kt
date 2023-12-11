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

    private fun setSocketChannelNames() {
        viewModel.subscribeSocket(listOf("", "", "", ""))
    }

    private fun setSocketEvent() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect {
                    when (it) {
                        SocketStateManager.Connected -> {
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
                        }
                    }
                }
        }
    }
}