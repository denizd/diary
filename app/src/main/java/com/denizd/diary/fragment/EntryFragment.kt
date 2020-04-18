package com.denizd.diary.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.denizd.diary.R
import com.denizd.diary.databinding.FragmentEntryBinding
import com.denizd.diary.model.Entry
import com.denizd.diary.util.asDate
import com.denizd.diary.util.viewBinding
import com.denizd.diary.viewmodel.EntryViewModel
import java.lang.IllegalArgumentException

class EntryFragment : BaseFragment(R.layout.fragment_entry) {

    private val binding by viewBinding(FragmentEntryBinding::bind)
    private val viewModel by viewModels<EntryViewModel>()
    private lateinit var currentEntry: Entry

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val id = arguments?.getLong("id")
        currentEntry = viewModel.getEntry(id ?: throw IllegalArgumentException("Entry with id $id not found"))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            titleField.setText(currentEntry.title)
            emotionField.setText(currentEntry.emotion)
            contentField.setText(currentEntry.content)
            val keyListener = contentField.keyListener
            contentField.keyListener = null
            lastEdited.text = currentEntry.time.asDate(time = true)
            backButton.setOnClickListener {
                activity?.onBackPressed()
            }
            editButton.setOnClickListener {
                contentField.isEditable = !contentField.isEditable
                editButton.setImageDrawable(context.getDrawable(if (contentField.isEditable) {
                    contentField.keyListener = keyListener
                    R.drawable.check
                } else {
                    contentField.keyListener = null
                    R.drawable.edit
                }))
                contentField.refreshText()
            }
        }
    }


    override fun onPause() {
        super.onPause()
        viewModel.updateEntry(with(binding) {
            currentEntry.copy(
                title = titleField.text.toString(),
                emotion = emotionField.text.toString(),
                content = contentField.text.toString()
            )
        })
    }
}