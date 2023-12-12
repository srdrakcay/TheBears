package com.serdar.profile.ui

import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.serdar.common.base.BaseFragment
import com.serdar.navigation.R
import com.serdar.profile.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate){
    private val viewModel by viewModels<ProfileViewModel>()

    override fun observeUi() {
        super.observeUi()
        viewModel.userInfo()
        initObserve()
        signOut()
    }
    private fun initObserve(){
        viewModel.viewModelScope.launch {
            viewModel.currentUser.collect(){
                binding.txtUserEmailInfo.text=it
            }
        }
        viewModel.viewModelScope.launch{
            viewModel.signOutState.collect(){
                when(it){
                    is SignOutState.Error->{

                    }
                    is SignOutState.Loading->{

                    }
                    is SignOutState.Success->{
                        findNavController().navigate(R.id.action_profileFragment_to_signInFragment)
                    }

                    else -> {

                    }
                }
            }
        }
    }
    private fun signOut(){
        binding.btnSignOut.setOnClickListener {
            viewModel.signOut()
        }
    }
}