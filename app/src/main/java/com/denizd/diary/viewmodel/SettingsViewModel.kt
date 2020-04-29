package com.denizd.diary.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingsViewModel : BaseViewModel() {

    private fun sync(update: SyncUpdate, operation: () -> Boolean) = viewModelScope.launch {
        withContext(Dispatchers.Main) {
            update.onSyncStarted()
        }
        val success = withContext(Dispatchers.IO) {
            operation()
        }
        withContext(Dispatchers.Main) {
            update.onSyncEnded(success)
        }
    }

    fun syncDown(update: SyncUpdate) {
        sync(update) {
            repo.syncDown()
        }
    }

    fun syncUp(update: SyncUpdate) {
        sync(update) {
            repo.syncUp()
        }
    }

    interface SyncUpdate {
        fun onSyncStarted()
        fun onSyncEnded(success: Boolean)
    }

}