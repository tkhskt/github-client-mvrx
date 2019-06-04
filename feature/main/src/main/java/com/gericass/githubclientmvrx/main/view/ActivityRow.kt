package com.gericass.githubclientmvrx.main.view

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import com.bumptech.glide.Glide
import com.gericass.githubclientmvrx.data.model.ReceiveEvent
import com.gericass.githubclientmvrx.main.R

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class ActivityRow @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val userImage: ImageView
    private val userName: TextView
    private val action: TextView
    private val repoName: TextView
    private val time: TextView


    init {
        inflate(context, R.layout.row_activity, this)
        userImage = findViewById(R.id.user_image)
        userName = findViewById(R.id.user_name)
        action = findViewById(R.id.action)
        repoName = findViewById(R.id.repo_name)
        time = findViewById(R.id.time)
    }

    @TextProp
    fun setUserImage(userImage: CharSequence) {
        Glide.with(this.userImage)
                .load(userImage)
                .centerCrop()
                .into(this.userImage)
    }

    @TextProp
    fun setUserName(userName: CharSequence) {
        this.userName.text = userName
    }

    @TextProp
    fun setRepoName(repoName: CharSequence) {
        this.repoName.text = repoName
    }

    @TextProp
    fun setTime(time: CharSequence) {
        this.time.text = time
    }

    @ModelProp
    fun setEvent(event: ReceiveEvent) {
        when (event.type) {
            "WatchEvent" -> action.text = "starred"
            "ForkEvent" -> action.text = "forked ${event.payload.forkee.full_name} from"
            "FollowEvent" -> action.text = "started following ${event.payload.target.login}"
            "CreateEvent" -> action.text =
                    "created repository"
        }
    }

    @CallbackProp
    fun setClickListener(clickListener: OnClickListener?) {
        setOnClickListener(clickListener)
    }
}