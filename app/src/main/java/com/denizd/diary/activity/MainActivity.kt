package com.denizd.diary.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Lifecycle
import com.denizd.diary.R
import com.denizd.diary.data.AppRepository
import com.denizd.diary.databinding.ActivityMainBinding
import com.denizd.diary.fragment.*
import com.denizd.diary.util.viewBinding
import com.denizd.diary.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)
    private var isAuthenticated = false
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        AppRepository.init(applicationContext)

        promptForLogin()
    }

    override fun onResume() {
        super.onResume()
        if (supportFragmentManager.fragments.size == 0) {
            loadFragment(if (isAuthenticated) "OverviewFragment" else "LoginFragment")
        }
    }

    override fun onBackPressed() {
        val currentFragment = supportFragmentManager.fragments[0]
        if (currentFragment is EntryFragment) {
            if (!currentFragment.hideEmojiKeyboard()) super.onBackPressed()
        } else {
            super.onBackPressed()
        }
    }

    private fun loadFragment(name: String): Boolean {
        val fragment = supportFragmentManager.findFragmentByTag(name) as? BaseFragment ?: viewModel.getNewFragment(name)

        if (fragment.lifecycle.currentState != Lifecycle.State.INITIALIZED) return false

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment, fragment.name)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
        return true
    }

    fun promptForLogin() {
        BiometricPrompt(this, ContextCompat.getMainExecutor(this),
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    isAuthenticated = true
                    loadFragment("OverviewFragment")
                }
            }
        ).authenticate(BiometricPrompt.PromptInfo.Builder()
            .setTitle(getString(R.string.biometrics_title))
            .setSubtitle(getString(R.string.biometrics_subtitle))
            .setDeviceCredentialAllowed(true)
            .build()
        )
    }
}