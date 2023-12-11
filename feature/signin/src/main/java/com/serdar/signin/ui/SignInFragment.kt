package com.serdar.signin.ui

import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.serdar.common.base.BaseFragment
import com.serdar.navigation.R
import com.serdar.signin.databinding.FragmentSignInBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignInBinding>(FragmentSignInBinding::inflate) {
    private lateinit var firebaseAuth: FirebaseAuth
    override fun observeUi() {
        super.observeUi()
        setCurrentUser()
    }

    private fun setCurrentUser() {
        firebaseAuth = FirebaseAuth.getInstance()
        if (firebaseAuth.currentUser != null) {
            findNavController().navigate(R.id.action_signInFragment_to_homeFragment)
        } else {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)

        }
    }
}