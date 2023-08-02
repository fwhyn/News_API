package com.fwhyn.noos.basecomponent.baseclass

import android.view.View
import com.fwhyn.noos.databinding.ViewProgressBarBinding

class ProgressBarView(private val viewBinding: ViewProgressBarBinding) {

    fun showLayout(flag: Boolean) {
        if (flag) {
            viewBinding.layoutProgressBar.visibility = View.VISIBLE
        } else {
            viewBinding.layoutProgressBar.visibility = View.GONE
        }

    }
}