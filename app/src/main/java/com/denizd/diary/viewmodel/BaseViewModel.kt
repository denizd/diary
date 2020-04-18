package com.denizd.diary.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.denizd.diary.data.AppRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

open class BaseViewModel : ViewModel() {

    protected val repo = AppRepository.instance

    inline fun doAsync(crossinline function: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) { function.invoke() }
    }

    inline fun <T> returnBlocking(crossinline function: () -> T): T = runBlocking(Dispatchers.IO) {
        function.invoke()
    }
}