package com.denizd.diary.fragment

import android.content.Context
import android.content.res.Configuration
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.denizd.diary.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

open class BaseFragment(@LayoutRes layoutId: Int) : Fragment(layoutId) {

    private lateinit var _context: Context

    val name: String = BaseFragment::class.java.simpleName

    override fun onAttach(context: Context) {
        super.onAttach(context)
        _context = context
    }

    /**
     * Overwritten to return non-null context after onAttach has been invoked. If called before
     * onAttach has been executed, an IllegalStateException will be thrown
     *
     * @return the context
     */
    override fun getContext(): Context = _context

    protected open fun getGridColumnCount(config: Configuration): Int {
        return when (config.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> 1
            else -> 2
        } + if (resources.getBoolean(R.bool.isTablet)) 1 else 0
    }

    protected fun openBottomSheet(sheet: BottomSheetDialogFragment) {
        sheet.setTargetFragment(this, 42)
        sheet.show(parentFragmentManager, sheet.javaClass.simpleName)
    }

    protected fun openFragment(fragment: BaseFragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment, fragment.name)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .addToBackStack(fragment.name)
            .commit()
    }
}