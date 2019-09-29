package io.coreflodev.exampleapplication.common.ui

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide

class NetworkImageView @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attr, defStyleAttr) {

    fun load(url: String) {
        Glide.with(context)
            .load(url)
            .into(this)
    }
}
