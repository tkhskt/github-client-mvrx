package com.gericass.githubclientmvrx.main


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.viewpager2.widget.ViewPager2
import com.airbnb.mvrx.BaseMvRxFragment
import com.gericass.githubclientmvrx.common.core.simpleController


class MainFragment : BaseMvRxFragment() {

    private lateinit var toolbar: Toolbar
    private lateinit var coordinatorLayout: CoordinatorLayout
    private lateinit var pager: ViewPager2
    private val epoxyController by lazy { epoxyController() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        epoxyController.onRestoreInstanceState(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false).apply {
            pager = findViewById(R.id.pager)
            toolbar = findViewById(R.id.toolbar)
            coordinatorLayout = findViewById(R.id.coordinator_layout)
            val adapter = PagerAdapter(childFragmentManager, this@MainFragment.lifecycle)
            listOf(TestFragment(), TestFragment(), TestFragment()).forEach {
                adapter.addFragment(it)
            }
            pager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            pager.adapter = adapter
        }
    }

    private fun epoxyController() = simpleController {}

    override fun invalidate() {}

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        epoxyController.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        epoxyController.cancelPendingModelBuild()
        super.onDestroyView()
    }
}
