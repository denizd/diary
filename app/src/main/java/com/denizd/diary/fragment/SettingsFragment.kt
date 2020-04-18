package com.denizd.diary.fragment

import android.os.Bundle
import android.view.View
import com.denizd.diary.R
import com.denizd.diary.databinding.FragmentSettingsBinding
import com.denizd.diary.util.viewBinding

class SettingsFragment : BaseFragment(R.layout.fragment_settings) {

    private val binding by viewBinding(FragmentSettingsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}