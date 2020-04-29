package com.denizd.diary.viewmodel

import com.denizd.diary.fragment.*

class MainViewModel : BaseViewModel() {

    fun getNewFragment(name: String): BaseFragment = when (name) {
        "LoginFragment" -> LoginFragment()
        "OverviewFragment" -> OverviewFragment()
        "EntryFragment" -> EntryFragment()
        "SettingsFragment" -> SettingsFragment()
        else -> throw IllegalArgumentException("Fragment $name not found")
    }
}