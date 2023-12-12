package com.serdar.profile.ui

import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.serdar.common.base.BaseFragment
import com.serdar.profile.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate){
    private val viewModel by viewModels<ProfileViewModel>()

    override fun observeUi() {
        super.observeUi()
    }
    private fun setUserInfo(){
        viewModel.viewModelScope.launch {
            viewModel.currentUser.collect(){

            }
        }
    }
    private fun signOut(){
        viewModel.signOut()
    }
}