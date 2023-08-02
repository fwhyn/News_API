package com.fwhyn.noos.basecomponent.baseclass

import android.view.View
import android.view.View.OnClickListener
import com.fwhyn.noos.databinding.ActivityMainBinding
import com.fwhyn.noos.databinding.ViewErrorBinding

class ErrorView(private val viewBinding: ViewErrorBinding) {

    fun showLayout(flag: Boolean) {
        if (flag) {
            viewBinding.layoutError.visibility = View.VISIBLE
        } else {
            viewBinding.layoutError.visibility = View.GONE
        }
    }

    fun setTitle(text: String) {
        viewBinding.tvErrorTitle.text = text
    }

    fun setMessage(text: String) {
        viewBinding.tvErrorMessage.text = text
    }

    fun setButton(onClickListener: OnClickListener, text: String = "") {
        if (text != "") {
            viewBinding.btnRetry.text = text
        }
        viewBinding.btnRetry.setOnClickListener(onClickListener)
    }
}