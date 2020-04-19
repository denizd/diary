package com.denizd.diary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.denizd.diary.databinding.ItemEntryBinding
import com.denizd.diary.model.Entry
import com.denizd.diary.util.asDate
import com.denizd.diary.util.asFormattedSpan

class EntryAdapter(private var entries: List<Entry>, private val listener: EntryClickListener) : RecyclerView.Adapter<EntryAdapter.EntryViewHolder>() {

    class EntryViewHolder(val binding: ItemEntryBinding, private val listener: EntryClickListener) : RecyclerView.ViewHolder(binding.root), View.OnClickListener, View.OnLongClickListener {

        lateinit var entry: Entry

        init {
            binding.root.also { root ->
                root.setOnClickListener(this)
                root.setOnLongClickListener(this)
            }
        }

        override fun onClick(v: View?) {
            listener.onEntryClick(entry)
        }

        override fun onLongClick(v: View?): Boolean {
            listener.onEntryLongClick(entry)
            return true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryViewHolder =
        EntryViewHolder(ItemEntryBinding.inflate(LayoutInflater.from(parent.context), parent, false), listener)

    override fun onBindViewHolder(holder: EntryViewHolder, position: Int) {
        entries[position].also { currentItem ->
            holder.entry = currentItem
            holder.binding.apply {
                title.text = currentItem.title
                contentPreview.text = currentItem.content.asFormattedSpan()
                time.text = currentItem.timeCreated.asDate(time = true)
                emotion.text = currentItem.emotion
            }
        }
    }

    override fun getItemCount(): Int = entries.size

    fun setEntries(entries: List<Entry>) {
        this.entries = entries
        notifyDataSetChanged()
    }

    interface EntryClickListener {
        fun onEntryClick(entry: Entry)
        fun onEntryLongClick(entry: Entry)
    }
}