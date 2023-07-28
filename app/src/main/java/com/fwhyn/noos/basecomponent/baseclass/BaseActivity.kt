package com.fwhyn.noos.basecomponent.baseclass

import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {
    private var launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        onActivityResult(result)
    }

    protected fun startActivityForResult(intent: Intent) {
        launcher.launch(intent)
    }

    protected fun onActivityResult(result: ActivityResult) {}
}