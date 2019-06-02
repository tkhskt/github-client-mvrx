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
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class MainFragment : BaseMvRxFragment() {

    private lateinit var toolbar: Toolbar
    private lateinit var coordinatorLayout: CoordinatorLayout
    private lateinit var pager: ViewPager2
    private lateinit var tab: TabLayout
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
            val pagerAdapter = PagerAdapter(childFragmentManager, this@MainFragment.lifecycle)
            listOf(TestFragment(), TestFragment(), TestFragment()).forEach {
                pagerAdapter.addFragment(it)
            }
            pager.apply {
                orientation = ViewPager2.ORIENTATION_HORIZONTAL
                adapter = pagerAdapter
                isUserInputEnabled = false
            }
            tab = findViewById(R.id.main_tab)
            setUpTab()
        }
    }

    private fun setUpTab() {
        // AutoRefreshは後からどっちか調整した方が良さげ
        TabLayoutMediator(tab, pager, true) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.activity)
                1 -> getString(R.string.overview)
                else -> getString(R.string.repositories)
            }
        }.attach()
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
