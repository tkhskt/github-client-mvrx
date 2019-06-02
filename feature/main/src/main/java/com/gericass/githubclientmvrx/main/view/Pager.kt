package com.gericass.githubclientmvrx.main.view

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.widget.ViewPager2
import com.airbnb.epoxy.AfterPropsSet
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.gericass.githubclientmvrx.main.PagerAdapter
import com.gericass.githubclientmvrx.main.R

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_MATCH_HEIGHT)
class Pager @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val pager: ViewPager2

    private lateinit var manager: FragmentManager
    private lateinit var owner: Lifecycle
    private lateinit var fragments: List<Fragment>

    init {
        inflate(context, R.layout.row_pager, this)
        pager = findViewById(R.id.pager)
        orientation = VERTICAL
    }

    @ModelProp(options = [ModelProp.Option.IgnoreRequireHashCode])
    fun setManager(manager: FragmentManager) {
        this.manager = manager
    }

    @ModelProp(options = [ModelProp.Option.IgnoreRequireHashCode])
    fun setOwner(owner: Lifecycle) {
        this.owner = owner
    }

    @ModelProp(options = [ModelProp.Option.IgnoreRequireHashCode])
    fun setFragments(fragments: List<Fragment>) {
        this.fragments = fragments
    }

    @AfterPropsSet
    fun setUpPager() {
        val adapter = PagerAdapter(manager, owner)
        fragments.forEach {
            adapter.addFragment(it)
        }
        pager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        pager.adapter = adapter
    }

    @CallbackProp
    fun setClickListener(clickListener: OnClickListener?) {
        setOnClickListener(clickListener)
    }


}