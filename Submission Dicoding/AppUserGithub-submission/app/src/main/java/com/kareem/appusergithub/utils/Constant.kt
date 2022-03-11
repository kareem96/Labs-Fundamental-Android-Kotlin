package com.kareem.appusergithub.utils

import androidx.annotation.StringRes
import com.kareem.appusergithub.R

object Constant {
    const val BASE_URL = "https://api.github.com/"
    const val API_KEY = "token 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"

    @StringRes
    val TABS_TITLES = intArrayOf(
        R.string.follower,
        R.string.following
    )
}