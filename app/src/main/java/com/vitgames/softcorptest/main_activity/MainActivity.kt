package com.vitgames.softcorptest.main_activity

import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.textview.MaterialTextView
import com.vitgames.softcorptest.MainApplication
import com.vitgames.softcorptest.R
import com.vitgames.softcorptest.RateDataInteractor
import com.vitgames.softcorptest.databinding.ActivityMainBinding
import javax.inject.Inject


class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var interactor: RateDataInteractor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initScreenWindow()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        (applicationContext as MainApplication).appComponent.inject(this)
        initSpinner()
        initBottomMenu()
        binding.spinner.onItemSelectedListener = this

        binding.sortButton.setOnClickListener {
            // TODO(Баг - нажатие на боттом меню не вызывает другие фрагменты)
            findNavController(R.id.fragment).navigate(R.id.action_global_sortedFragment)
        }
    }

    private fun initScreenWindow() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val base = (p1 as MaterialTextView).text.toString()
        interactor.setBaseRate(base)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}

    private fun initSpinner() {
        ArrayAdapter.createFromResource(
            this, R.array.rates_array, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinner.adapter = adapter
        }
    }

    private fun initBottomMenu() {
        val bottomMenu = binding.navView
        val navController = findNavController(R.id.fragment)
        bottomMenu.setupWithNavController(navController)
    }
}