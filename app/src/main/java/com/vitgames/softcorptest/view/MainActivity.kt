package com.vitgames.softcorptest.view

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowInsetsController
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.vitgames.softcorptest.MainApplication
import com.vitgames.softcorptest.R
import com.vitgames.softcorptest.databinding.ActivityMainBinding
import com.vitgames.softcorptest.domain.MainViewModel
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    @Inject
    lateinit var modelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initScreenWindow()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        (applicationContext as MainApplication).appComponent.inject(this)
        viewModel = ViewModelProviders.of(this, modelFactory)[MainViewModel::class.java]

        initRateSpinner()
        initBottomMenu()
        initAmountEditInput()
        handleDefaultRequest()
    }

    private fun initAmountEditInput() {
        val amountInput = binding.amountInput
        amountInput.doAfterTextChanged {
            viewModel.setInputAfterTextChangedListener(amountInput.text.toString())
        }

        viewModel.userInputData.observe(this) { newInput ->
           binding.amountInput.setText(newInput)
        }
    }

    private fun handleDefaultRequest() {
        viewModel.sendRequest()
    }

    private fun initScreenWindow() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    private fun initRateSpinner() {
        ArrayAdapter.createFromResource(
            this, R.array.rates_array, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.rateSpinner.adapter = adapter
        }

        binding.rateSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parentView: AdapterView<*>?,
                    selectedItemView: View?,
                    position: Int,
                    id: Long
                ) {
                    val newRate = (selectedItemView as TextView).text.toString()
                    viewModel.setRateName(newRate)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }

    private fun initBottomMenu() {
        val bottomMenu = binding.navView
        val navController = findNavController(R.id.fragment)
        bottomMenu.setupWithNavController(navController)
    }
}