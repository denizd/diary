package com.denizd.diary.fragment

import android.animation.Animator
import android.os.Bundle
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.Animation
import androidx.fragment.app.viewModels
import com.denizd.diary.R
import com.denizd.diary.databinding.FragmentEntryBinding
import com.denizd.diary.model.Entry
import com.denizd.diary.util.asDate
import com.denizd.diary.util.viewBinding
import com.denizd.diary.viewmodel.EntryViewModel
import kotlin.math.hypot

class EntryFragment : BaseFragment(R.layout.fragment_entry) {

    private val binding by viewBinding(FragmentEntryBinding::bind)
    private val viewModel by viewModels<EntryViewModel>()
    private lateinit var currentEntry: Entry
    private var originalTitle = ""
    private var originalText = ""
    private var originalEmotion = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val id = arguments?.getLong("id")
        currentEntry = viewModel.getEntry(id ?: throw IllegalArgumentException("Entry with id $id not found"))
        originalTitle = currentEntry.title
        originalText = currentEntry.content
        originalEmotion = currentEntry.emotion
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            titleField.setText(currentEntry.title)
            emotionField.setText(currentEntry.emotion)
            contentField.setText(currentEntry.content)
            val keyListener = contentField.keyListener
            contentField.keyListener = null
            created.text = getCreatedString()
            lastModified.text = getLastModifiedString()
            backButton.setOnClickListener {
                activity?.onBackPressed()
            }
            editButton.setOnClickListener {
                contentField.isEditable = !contentField.isEditable
                reveal(contentField.isEditable)
                editButton.setImageDrawable(context.getDrawable(if (contentField.isEditable) {
                    contentField.keyListener = keyListener
                    R.drawable.check
                } else {
                    save()
                    contentField.keyListener = null
                    R.drawable.edit
                }))
                contentField.refreshText()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        save()
    }

    private fun reveal(isInEditMode: Boolean) {
        with(binding) {
            val endRadius = hypot(revealView.width.toDouble(), revealView.height.toDouble()).toFloat()
            ViewAnimationUtils.createCircularReveal(
                    revealView,
                    editButton.left + editButton.width / 2,
                    editButton.top + editButton.height / 2,
                    if (isInEditMode) 0F else endRadius,
                    if (isInEditMode) endRadius else 0F
            ).apply {
                addListener(object : Animator.AnimatorListener {
                    override fun onAnimationRepeat(animation: Animator?) {}
                    override fun onAnimationEnd(animation: Animator?) {
                        revealView.visibility = if (isInEditMode) View.VISIBLE else View.INVISIBLE
                    }
                    override fun onAnimationCancel(animation: Animator?) {}
                    override fun onAnimationStart(animation: Animator?) {
                        if (isInEditMode) revealView.visibility = View.VISIBLE
                    }
                })
                start()
            }
        }
    }

    private fun save() {
        if (hasBeenEdited()) {
            viewModel.updateEntry(with(binding) {
                currentEntry.copy(
                        title = titleField.text.toString(),
                        emotion = emotionField.text.toString(),
                        content = contentField.text.toString(),
                        timeLastModified = currentEntry.timeLastModified
                )
            })
        }
    }

    private fun hasBeenEdited(): Boolean {
        return if (originalTitle == binding.titleField.text.toString()
                && originalText == binding.contentField.text.toString()
                && originalEmotion == binding.emotionField.text.toString()) {
            false
        } else {
            originalTitle = binding.titleField.text.toString()
            originalText = binding.contentField.text.toString()
            originalEmotion = binding.emotionField.text.toString()
            currentEntry = currentEntry.copy(timeLastModified = System.currentTimeMillis())
            binding.lastModified.text = getLastModifiedString()
            true
        }
    }

    private fun getCreatedString(): String =
            getString(R.string.created_placeholder, currentEntry.timeCreated.asDate(time = true))
    private fun getLastModifiedString(): String =
            getString(R.string.modified_placeholder, currentEntry.timeLastModified.asDate(time = true))
}