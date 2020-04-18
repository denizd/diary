package com.denizd.diary.fragment

import android.os.Bundle
import android.view.View
import com.denizd.diary.R
import com.denizd.diary.activity.MainActivity
import com.denizd.diary.databinding.FragmentLoginBinding
import com.denizd.diary.util.viewBinding

class LoginFragment : BaseFragment(R.layout.fragment_login) {

    private val binding by viewBinding(FragmentLoginBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.promptButton.setOnClickListener {
            (activity as? MainActivity)?.promptForLogin()
        }
    }
}