package com.yeonberry.search_users.util

import android.content.Context

fun dpToPx(context: Context, dp: Int): Int {
    return (dp * context.resources.displayMetrics.density).toInt()
}