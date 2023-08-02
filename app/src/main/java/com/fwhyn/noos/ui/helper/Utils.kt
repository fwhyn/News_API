package com.fwhyn.noos.ui.helper

import android.content.Context
import android.widget.Toast
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.fwhyn.noos.R

object Utils {

}

fun Context.notifyIfListEmpty(data: List<Any>) {
    if (data.isEmpty()) {
        showToast(getString(R.string.data_empty))
    }
}

fun Context.showToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun SwipeRefreshLayout.enableSwipeRefresh(flag: Boolean) {
    this.isRefreshing = flag
    this.isEnabled = flag
}