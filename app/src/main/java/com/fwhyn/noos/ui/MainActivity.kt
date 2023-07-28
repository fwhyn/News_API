package com.fwhyn.noos.ui

import android.os.Bundle
import com.fwhyn.noos.basecomponent.baseclass.BaseActivityBinding
import com.fwhyn.noos.databinding.ActivityMainBinding

class MainActivity : BaseActivityBinding<ActivityMainBinding>() {
    override fun onBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}