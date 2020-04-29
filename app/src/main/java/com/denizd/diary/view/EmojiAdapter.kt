package com.denizd.diary.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.denizd.diary.databinding.ItemEmojiBinding
import com.denizd.diary.databinding.ItemEntryBinding
import java.nio.charset.Charset

class EmojiAdapter(private val listener: EmojiClickListener) :
    RecyclerView.Adapter<EmojiAdapter.EmojiViewHolder>() {

    class EmojiViewHolder(val binding: ItemEmojiBinding, private val listener: EmojiClickListener) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        var emoji: String = ""

        init {
            binding.root.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener.onEmojiClick(emoji)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmojiViewHolder =
        EmojiViewHolder(
            ItemEmojiBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), listener
        )

    override fun onBindViewHolder(holder: EmojiViewHolder, position: Int) {
        emojis[position].also { currentItem ->
            holder.emoji = currentItem
            holder.binding.emoji.text = currentItem
        }
    }

    override fun getItemCount(): Int = emojis.size

    interface EmojiClickListener {
        fun onEmojiClick(emoji: String)
    }

    private val emojis: Array<String> = arrayOf(
        "😀",
        "😁",
        "😂",
        "🤣",
        "😃",
        "😄",
        "😅",
        "😆",
        "😉",
        "😊",
        "😋",
        "😎",
        "😍",
        "😘",
        "🥰",
        "😗",
        "😙",
        "😚",
        "☺",
        "🙂",
        "🤗",
        "🤩",
        "🤔",
        "🤨",
        "😐",
        "😑",
        "😶",
        "🙄",
        "😏",
        "😣",
        "😥",
        "😮",
        "🤐",
        "😯",
        "😪",
        "😫",
//        "\uD83E\uDD71",
        "😴",
        "😌",
        "😛",
        "😜",
        "😝",
        "🤤",
        "😒",
        "😓",
        "😔",
        "😕",
        "🙃",
        "🤑",
        "😲",
//        "☹",
        "🙁",
        "😖",
        "😞",
        "😟",
        "😤",
        "😢",
        "😭",
        "😦",
        "😧",
        "😨",
        "😩",
        "🤯",
        "😬",
        "😰",
        "😱",
        "🥵",
        "🥶",
        "😳",
        "🤪",
        "😵",
        "🥴",
        "😠",
        "😡",
        "🤬",
        "😷",
        "🤒",
        "🤕",
        "🤢",
        "🤮",
        "🤧",
        "😇",
        "🥳",
        "🥺",
        "🤠",
        "🤡",
        "🤥",
        "🤫",
        "🤭",
        "🧐",
        "🤓",
        "😈",
        "👿",
        "❤",
        "💌",
        "💔",
        "💤"
    )
}