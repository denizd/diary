package com.denizd.diary.viewmodel

import com.denizd.diary.model.Entry

class EntryViewModel : BaseViewModel() {

    fun getEntry(id: Long): Entry = returnBlocking { repo.db.getEntry(id) }
    fun updateEntry(entry: Entry) = doAsync { repo.db.updateEntry(entry) }
}