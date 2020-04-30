package com.denizd.diary.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.denizd.diary.R
import com.denizd.diary.databinding.DialogSyncBinding
import com.denizd.diary.databinding.FragmentSettingsBinding
import com.denizd.diary.util.viewBinding
import com.denizd.diary.viewmodel.SettingsViewModel
import com.google.android.material.textfield.TextInputEditText

class SettingsFragment : BaseFragment(R.layout.fragment_settings) {

    private val binding by viewBinding(FragmentSettingsBinding::bind)
    private val viewModel by viewModels<SettingsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.syncButton.setOnClickListener {
            DialogSyncBinding.inflate(layoutInflater).also { dialogBinding ->
                AlertDialog.Builder(context).apply {
                    setView(dialogBinding.root)
                }.show()
                dialogBinding.syncUpButton.setOnClickListener {
                    syncUp(dialogBinding.hostInput, dialogBinding.status)
                }
                dialogBinding.syncDownButton.setOnClickListener {
                    syncDown(dialogBinding.hostInput, dialogBinding.status)
                }
            }
        }
    }

    private fun syncUp(host: TextInputEditText, status: TextView) {
        val address = host.text.toString().trim()
        if (address.isEmpty()) {
            host.error = getString(R.string.host_empty)
        } else {
            viewModel.syncUp(address, object : SettingsViewModel.SyncUpdate {
                override fun onSyncStarted() {
                    status.text = getString(R.string.sync_up_started)
                }
                override fun onSyncEnded(success: Boolean) {
                    status.text =
                        getString(if (success) R.string.sync_up_success else R.string.sync_error)
                }
            })
        }
    }

    private fun syncDown(host: TextInputEditText, status: TextView) {
        val address = host.text.toString().trim()
        if (address.isEmpty()) {
            host.error = getString(R.string.host_empty)
        } else {
            viewModel.syncDown(address, object : SettingsViewModel.SyncUpdate {
                override fun onSyncStarted() {
                    status.text = getString(R.string.sync_down_started)
                }
                override fun onSyncEnded(success: Boolean) {
                    status.text =
                        getString(if (success) R.string.sync_down_success else R.string.sync_error)
                }
            })
        }
    }
}