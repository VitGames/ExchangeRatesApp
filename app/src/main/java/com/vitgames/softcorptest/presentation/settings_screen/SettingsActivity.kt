package com.vitgames.softcorptest.presentation.settings_screen

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.vitgames.softcorptest.MainApplication
import com.vitgames.softcorptest.databinding.ActivitySettingsBinding
import javax.inject.Inject

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private val viewModel by viewModels<SettingsViewModel> { modelFactory }

    @Inject
    lateinit var modelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initScreenWindow()

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        (applicationContext as MainApplication).appComponent.inject(this)
    }

    private fun initScreenWindow() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }
}