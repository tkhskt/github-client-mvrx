package com.gericass.githubclientmvrx.main


import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatEditText
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.airbnb.mvrx.BaseMvRxFragment
import com.airbnb.mvrx.activityViewModel
import com.gericass.githubclientmvrx.common.core.simpleController
import com.gericass.githubclientmvrx.common.navigator.Navigtor
import com.gericass.githubclientmvrx.main.activity.ActivityFragment
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import org.koin.android.ext.android.inject


class MainFragment : BaseMvRxFragment() {

    private lateinit var coordinatorLayout: CoordinatorLayout
    private lateinit var pager: ViewPager2
    private lateinit var tab: TabLayout
    private lateinit var appBar: AppBarLayout
    private lateinit var searchEditText: AppCompatEditText
    private lateinit var searchButton: ImageView
    private val epoxyController by lazy { epoxyController() }

    private val mainViewModel: MainViewModel by activityViewModel()

    private val navigator: Navigtor.MainNavigator by inject()

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
            pager = findViewById(R.id.main_pager)
            coordinatorLayout = findViewById(R.id.coordinator_layout)
            val pagerAdapter = PagerAdapter(childFragmentManager, this@MainFragment.lifecycle)
            listOf(ActivityFragment(), TestFragment(), TestFragment()).forEach {
                pagerAdapter.addFragment(it)
            }
            pager.apply {
                orientation = ViewPager2.ORIENTATION_HORIZONTAL
                adapter = pagerAdapter
                isUserInputEnabled = false
            }
            searchEditText = findViewById(R.id.search)
            searchButton = findViewById(R.id.search_button)
            tab = findViewById(R.id.main_tab)
            appBar = findViewById(R.id.app_bar)
            setUpKeyBoard()
            setUpTab()
            setUpSearch()
        }
    }

    private fun closeKeyBoard() {
        val imm = ContextCompat.getSystemService(
            requireContext(),
            InputMethodManager::class.java
        )
        imm?.hideSoftInputFromWindow(searchEditText.windowToken, 0)
    }

    private fun setUpKeyBoard() {
        KeyboardVisibilityEvent.setEventListener(requireActivity()) { visible ->
            if (!visible) {
                searchEditText.isFocusable = false
                searchEditText.isFocusableInTouchMode = true
            }
        }
        searchEditText.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER || event.keyCode == KeyEvent.KEYCODE_BACK) {
                closeKeyBoard()
                searchEditText.isFocusable = false
                searchEditText.isFocusableInTouchMode = true
                true
            } else {
                false
            }
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

    private fun setUpSearch() {
        searchButton.setOnClickListener {
            val text = searchEditText.text.toString()
            if (text.isEmpty()) return@setOnClickListener
            closeKeyBoard()
            navigator.run {
                findNavController().navigateToSearch(text)
            }
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
