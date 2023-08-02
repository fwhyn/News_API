package com.fwhyn.noos.ui.helper

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.widget.Toast
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