package com.denizd.diary.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import com.denizd.diary.R
import com.denizd.diary.data.AppRepository
import com.denizd.diary.databinding.ActivityMainBinding
import com.denizd.diary.fragment.BaseFragment
import com.denizd.diary.fragment.EntryFragment
import com.denizd.diary.fragment.OverviewFragment
import com.denizd.diary.fragment.SettingsFragment
import com.denizd.diary.util.viewBinding
import kotlin.IllegalArgumentException

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        AppRepository.init(applicationContext)
    }

    override fun onResume() {
        super.onResume()
        if (supportFragmentManager.fragments.size == 0) loadFragment("OverviewFragment")
    }

    private fun loadFragment(name: String): Boolean {
        val fragment = supportFragmentManager.findFragmentByTag(name) as? BaseFragment ?: getNewFragment(name)

        if (fragment.lifecycle.currentState != Lifecycle.State.INITIALIZED) return false

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment, fragment.name)
            .commit()
        return true
    }

    private fun getNewFragment(name: String): BaseFragment = when (name) {
        "OverviewFragment" -> OverviewFragment()
        "EntryFragment" -> EntryFragment()
        "SettingsFragment" -> SettingsFragment()
        else -> throw IllegalArgumentException("Fragment $name not found")
    }
}