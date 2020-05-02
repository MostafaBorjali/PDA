package com.okala.test.ui.fragment.login

import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.okala.test.R
import com.okala.test.databinding.FragmentLoginBinding
import com.okala.test.utils.base.BaseFragment


class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val loginViewModel: LoginViewModel by lazy {
        ViewModelProviders.of(this).get(LoginViewModel::class.java)
    }

    override fun getLayoutResourceId() = R.layout.fragment_login

    override fun oncreate() {
      binding.btnLogin.setOnClickListener {
          findNavController().navigate(R.id.blankFragment)
      }
        }

    }


