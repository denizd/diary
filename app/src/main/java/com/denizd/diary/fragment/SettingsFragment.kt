package com.denizd.diary.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.denizd.diary.R
import com.denizd.diary.databinding.DialogSyncBinding
import com.denizd.diary.databinding.FragmentSettingsBinding
import com.denizd.diary.util.viewBinding
import com.denizd.diary.viewmodel.SettingsViewModel

class SettingsFragment : BaseFragment(R.layout.fragment_settings) {

    private val binding by viewBinding(FragmentSettingsBinding::bind)
    private val viewModel by viewModels<SettingsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.syncButton.setOnClickListener {
//            syncDown()
        }
    }

    private fun syncDown() {
        val dialogBinding = DialogSyncBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(context).apply {
            setView(dialogBinding.root)
        }.create()
        dialog.show()
        viewModel.syncDown(object : SettingsViewModel.SyncUpdate {
            override fun onSyncStarted() {
                dialogBinding.tv.text = "started"
            }
            override fun onSyncEnded(success: Boolean) {
                dialogBinding.tv.text = "success = $success"
            }
        })
    }

    private fun syncUp() {
        val dialogBinding = DialogSyncBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(context).apply {
            setView(dialogBinding.root)
        }.create()
        dialog.show()
        viewModel.syncUp(object : SettingsViewModel.SyncUpdate {
            override fun onSyncStarted() {
                dialogBinding.tv.text = "started up"
            }
            override fun onSyncEnded(success: Boolean) {
                dialogBinding.tv.text = "success = $success"
            }
        })
    }
}