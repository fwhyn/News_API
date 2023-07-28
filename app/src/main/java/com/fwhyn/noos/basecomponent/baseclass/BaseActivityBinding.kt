package com.fwhyn.noos.basecomponent.baseclass

import android.os.Bundle
import androidx.viewbinding.ViewBinding

abstract class BaseActivityBinding<VB : ViewBinding> : BaseActivity() {

    protected lateinit var viewBinding: VB

    protected abstract fun onBinding(): VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = onBinding()
        setContentView(viewBinding.root)
    }
}