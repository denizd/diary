package com.denizd.diary.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingsViewModel : BaseViewModel() {

    private inline fun sync(update: SyncUpdate, crossinline operation: () -> Boolean) = viewModelScope.launch {
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

    fun syncDown(host: String, update: SyncUpdate) {
        sync(update) {
            repo.syncDown(host)
        }
    }

    fun syncUp(host: String, update: SyncUpdate) {
        sync(update) {
            repo.syncUp(host)
        }
    }

    interface SyncUpdate {
        fun onSyncStarted()
        fun onSyncEnded(success: Boolean)
    }

}