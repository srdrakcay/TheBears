package com.serdar.signin.ui

import android.util.Log
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.serdar.common.base.BaseFragment
import com.serdar.common.validation.EmptyTextRule
import com.serdar.common.validation.PasswordRule
import com.serdar.common.validation.validationRule
import com.serdar.navigation.R
import com.serdar.signin.databinding.FragmentSignInBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignInBinding>(FragmentSignInBinding::inflate) {
    private lateinit var firebaseAuth: FirebaseAuth
    override fun observeUi() {
        super.observeUi()
        setCurrentUser()
        binding.btnLogIn.setOnClickListener {

            Log.e("TAG", "observeUi: ${validateRule()}", )
        }
    }

    private fun setCurrentUser() {
        firebaseAuth = FirebaseAuth.getInstance()
        if (firebaseAuth.currentUser != null) {
            //findNavController().navigate(R.id.action_signInFragment_to_homeFragment)
        } else {
           // findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)

        }
    }
    private fun validateRule():Boolean{
        val email=binding.editTxtEmail.validationRule(
           rules = listOf(EmptyTextRule())
        )
        val password=binding.editTxtPassword.validationRule(
            rules = listOf(EmptyTextRule(),PasswordRule())
        )
        return email && password
    }
}