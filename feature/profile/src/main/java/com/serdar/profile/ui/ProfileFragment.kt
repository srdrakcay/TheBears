package com.serdar.profile.ui

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.serdar.common.base.BaseFragment
import com.serdar.navigation.R
import com.serdar.profile.databinding.FragmentProfileBinding
import com.serdar.socket.data.SocketStateManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {
    private val viewModel by viewModels<ProfileViewModel>()

    override fun callInitialViewModelFunctions() {
        super.callInitialViewModelFunctions()
        viewModel.userInfo()

    }

    override fun observeUi() {
        super.observeUi()
        initObserve()
        signOut()
        setSocketEvent()
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
                        findNavController().navigate(R.id.action_profileFragment_to_signInFragment)
                    }

                    else -> {

                    }
                }
            }
        }
    }

    private fun setSocketEvent() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect {
                    when (it) {
                        SocketStateManager.Connected -> {
                            binding.txtSocketStatus.text = "Socket Status: $it"
                        }

                        SocketStateManager.Connecting -> {
                            binding.txtSocketStatus.text = "Socket Status: $it"

                        }

                        SocketStateManager.Disconnected -> {
                            binding.txtSocketStatus.text = "Socket Status: $it"

                        }

                        SocketStateManager.Disconnecting -> {
                            binding.txtSocketStatus.text = "Socket Status: $it"

                        }

                        is SocketStateManager.Error -> {

                        }

                        is SocketStateManager.Price -> {

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
}