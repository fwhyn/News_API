package com.fwhyn.noos.ui.helper

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.widget.Toast
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.fwhyn.noos.data.models.Article
import com.fwhyn.noos.ui.helper.Constants
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Random

object Utils {

}

fun Context.showToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun SwipeRefreshLayout.enableSwipeRefresh(flag: Boolean) {
    this.isRefreshing = flag
    this.isEnabled = flag
}