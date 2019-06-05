package com.gericass.githubclientmvrx.common.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import com.gericass.githubclientmvrx.common.R

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class RepositoryRow @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val repoName: TextView
    private val description: TextView
    private val starCount: TextView


    init {
        inflate(context, R.layout.row_repository, this)
        description = findViewById(R.id.description)
        starCount = findViewById(R.id.star_count)
        repoName = findViewById(R.id.repo_name)
    }


    @TextProp
    fun setRepoName(repoName: CharSequence) {
        this.repoName.text = repoName
    }

    @TextProp
    fun setDescription(description: CharSequence) {
        this.description.text = description
    }

    @TextProp
    fun setStarCount(starCount: CharSequence) {
        this.starCount.text = starCount
    }

    @CallbackProp
    fun setClickListener(clickListener: View.OnClickListener?) {
        setOnClickListener(clickListener)
    }
}