package com.serdar.profile.ui

import android.util.Log
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.serdar.common.base.BaseFragment
import com.serdar.common.extensions.combineString
import com.serdar.profile.R
import com.serdar.navigation.R as navigationR
import com.serdar.profile.databinding.FragmentProfileBinding
import com.serdar.socket.data.SocketStateManager
import com.serdar.socket.util.Difference
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {
    private val viewModel by viewModels<ProfileViewModel>()
    private val adapter by lazy { ProfileAdapter() }
    companion object {
        const val PRICE_CHANNEL_NAME = "live_trades_btcusd"
    }
    override fun callInitialViewModelFunctions() {
        super.callInitialViewModelFunctions()
        viewModel.userInfo()
        viewModel.getAllCryptoDataFromRest()
        setAdapter()
    }

    override fun observeUi() {
        super.observeUi()
        initObserve()
        signOut()
        setSocketEvent()
        initCryptoPriceObserve()
    }

    private fun setAdapter() {
        binding.rcvCrypto.adapter = adapter
    }

    private fun initCryptoPriceObserve() {
        viewModel.viewModelScope.launch {
            viewModel.profileUiState.collect {
                when (it) {
                    is ProfileUiState.Error -> {
                    }

                    is ProfileUiState.Loading -> {
                    }

                    is ProfileUiState.Success -> {
                        Log.e("TAG", "initCryptoPriceObserve:${it.result.data} ", )
                        adapter.updateItems(it.result.data)
                    }
                }
            }
        }
    }

    private fun initObserve() {
        viewModel.viewModelScope.launch {
            viewModel.currentUser.collect {
                binding.txtUserEmailInfo.text = it
            }
        }
        viewModel.viewModelScope.launch {
            viewModel.signOutState.collect {
                when (it) {
                    is SignOutState.Error -> {

                    }

                    is SignOutState.Loading -> {

                    }

                    is SignOutState.Success -> {
                        findNavController().navigate(navigationR.id.action_profileFragment_to_signInFragment)
                    }

                    else -> {

                    }
                }
            }
        }
    }

    private fun setSocketEvent() {
        val socketStatus = resources.getString(com.serdar.localization.R.string.socket_status)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect {
                    when (it) {
                        SocketStateManager.Connected -> {
                            binding.txtSocketStatus.text = socketStatus.combineString(it.toString())
                        }

                        SocketStateManager.Connecting -> {
                            binding.txtSocketStatus.text = socketStatus.combineString(it.toString())

                        }

                        SocketStateManager.Disconnected -> {
                            binding.txtSocketStatus.text = socketStatus.combineString(it.toString())

                        }

                        SocketStateManager.Disconnecting -> {
                            binding.txtSocketStatus.text = socketStatus.combineString(it.toString())

                        }

                        is SocketStateManager.Error -> {

                        }

                        is SocketStateManager.Price -> {
                            if (it.channel == PRICE_CHANNEL_NAME) {
                                setExchange(it.exchange)
                                binding.txtSocketStatus.text =
                                    resources.getString(com.serdar.localization.R.string.socket_btc)
                                        .combineString(it.value.toString())
                            }
                        }

                        else -> {}
                    }
                }
        }
    }

    private fun signOut() {
        binding.btnSignOut.setOnClickListener {
            viewModel.signOut()
        }
    }
    private fun setExchange(difference: Difference) {
        when (difference) {
            Difference.UP -> {
                binding.txtSocketStatus.setTextColor(
                    AppCompatResources.getColorStateList(
                        requireContext(),R.color.percentageUp
                    )
                )
            }

            Difference.Down -> {
                binding.txtSocketStatus.setTextColor(
                    AppCompatResources.getColorStateList(
                        requireContext(), R.color.percentageDown
                    )
                )
            }

            Difference.Stable -> {
                binding.txtSocketStatus.setTextColor(
                    AppCompatResources.getColorStateList(
                        requireContext(), R.color.black
                    )
                )
            }
        }
    }
}