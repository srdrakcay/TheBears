package com.serdar.profile.ui

import androidx.fragment.app.viewModels
import com.serdar.common.base.BaseFragment
import com.serdar.profile.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate){
    private val viewModel by viewModels<ProfileViewModel>()

}