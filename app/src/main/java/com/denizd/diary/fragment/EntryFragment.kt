package com.denizd.diary.fragment

import android.animation.Animator
import android.app.Activity
import android.inputmethodservice.InputMethodService
import android.os.Bundle
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.Animation
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.denizd.diary.R
import com.denizd.diary.databinding.FragmentEntryBinding
import com.denizd.diary.model.Entry
import com.denizd.diary.util.asDate
import com.denizd.diary.util.viewBinding
import com.denizd.diary.view.EmojiAdapter
import com.denizd.diary.viewmodel.EntryViewModel
import kotlin.math.hypot

class EntryFragment : BaseFragment(R.layout.fragment_entry), EmojiAdapter.EmojiClickListener {

    private val binding by viewBinding(FragmentEntryBinding::bind)
    private val viewModel by viewModels<EntryViewModel>()
    private lateinit var currentEntry: Entry
    private var originalTitle = ""
    private var originalText = ""
    private var originalEmotion = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        currentEntry = viewModel.getEntry(arguments?.getLong("id"))
        originalTitle = currentEntry.title
        originalText = currentEntry.content
        originalEmotion = currentEntry.emotion
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            titleField.setText(currentEntry.title)
            emojiText.text = currentEntry.emotion
            emojiKeyboard.initialise(this@EntryFragment)
            contentField.setText(currentEntry.content)
            val keyListener = contentField.keyListener
            contentField.keyListener = null
            emojiButton.isEnabled = false
            created.text = getCreatedString()
            lastModified.text = getLastModifiedString()
            backButton.setOnClickListener {
                activity?.onBackPressed()
            }
            editButton.setOnClickListener {
                hideEmojiKeyboard()
                contentField.isEditable = !contentField.isEditable
                reveal(contentField.isEditable)
                editButton.setImageDrawable(context.getDrawable(if (contentField.isEditable) {
                    contentField.keyListener = keyListener
                    emojiButton.isEnabled = true
                    R.drawable.check
                } else {
                    save()
                    contentField.keyListener = null
                    emojiButton.isEnabled = false
                    R.drawable.edit
                }))
                contentField.refreshText()
            }
            titleField.setOnClickListener {
                hideEmojiKeyboard()
            }
            emojiButton.setOnClickListener {
                emojiKeyboard.visibility = if (emojiKeyboard.visibility == View.VISIBLE) {
                    View.GONE
                } else {
                    (context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager)
                        .hideSoftInputFromWindow(view.windowToken, 0)
                    View.VISIBLE
                }
            }
            contentField.setOnClickListener {
                hideEmojiKeyboard()
            }
        }
    }

    override fun onPause() {
        hideEmojiKeyboard()
        save()
        super.onPause()
    }

    private fun save() {
        if (hasBeenEdited()) with(binding) {
            viewModel.save(
                currentEntry,
                titleField.text.toString(),
                emojiText.text.toString(),
                contentField.text.toString()
            )
        }
    }

    fun hideEmojiKeyboard(): Boolean {
        with (binding.emojiKeyboard) {
            if (visibility == View.GONE) return false
            visibility = View.GONE
            return true
        }
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

    private fun hasBeenEdited(): Boolean {
        return if (originalTitle == binding.titleField.text.toString()
                && originalText == binding.contentField.text.toString()
                && originalEmotion == binding.emojiText.text.toString()) {
            false
        } else {
            originalTitle = binding.titleField.text.toString()
            originalText = binding.contentField.text.toString()
            originalEmotion = binding.emojiText.text.toString()
            currentEntry = currentEntry.copy(timeLastModified = System.currentTimeMillis())
            binding.lastModified.text = getLastModifiedString()
            true
        }
    }

    private fun getCreatedString(): String =
            getString(R.string.created_placeholder, currentEntry.timeCreated.asDate(time = true))
    private fun getLastModifiedString(): String =
            getString(R.string.modified_placeholder, currentEntry.timeLastModified.asDate(time = true))

    override fun onEmojiClick(emoji: String) {
        binding.emojiText.text = emoji
    }
}