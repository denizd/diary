package com.denizd.diary.viewmodel

import com.denizd.diary.model.Entry

class EntryViewModel : BaseViewModel() {

    fun getEntry(id: Long?): Entry = id?.let {
        returnBlocking { repo.db.getEntry(id) }
    } ?: throw IllegalArgumentException("Entry with id $id not found")
    private fun updateEntry(entry: Entry) = doAsync { repo.db.updateEntry(entry) }

    fun save(currentEntry: Entry, title: String, emoji: String, content: String) {
        updateEntry(currentEntry.copy(
            title = title,
            emotion = emoji,
            content = content,
            timeLastModified = currentEntry.timeLastModified
        ))
    }
}