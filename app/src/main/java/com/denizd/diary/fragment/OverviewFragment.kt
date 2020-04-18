package com.denizd.diary.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.denizd.diary.R
import com.denizd.diary.adapter.EntryAdapter
import com.denizd.diary.databinding.FragmentOverviewBinding
import com.denizd.diary.model.Entry
import com.denizd.diary.util.viewBinding
import com.denizd.diary.viewmodel.OverviewViewModel

class OverviewFragment : BaseFragment(R.layout.fragment_overview), EntryAdapter.EntryClickListener {

    private val binding by viewBinding(FragmentOverviewBinding::bind)
    private val viewModel by viewModels<OverviewViewModel>()
    private val entryAdapter = EntryAdapter(listOf(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.allEntries.observe(this, Observer { entries ->
            entryAdapter.setEntries(entries)
            if (savedInstanceState == null) entryAdapter.notifyDataSetChanged()
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            addButton.setOnClickListener {
                openEntry(viewModel.insert(Entry.empty))
            }
            settingsButton.setOnClickListener {
                openFragment(SettingsFragment())
            }
            recyclerView.apply {
                adapter = entryAdapter
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }
        }
    }

    private fun openEntry(id: Long) {
        openFragment(EntryFragment().also { fragment ->
            fragment.arguments = Bundle().also { bundle ->
                bundle.putLong("id", id)
            }
        })
    }

    override fun onEntryClick(entry: Entry) {
        openEntry(entry.id)
    }

    override fun onEntryLongClick(entry: Entry) {

    }
}