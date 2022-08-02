package com.vitgames.softcorptest.view

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import com.vitgames.softcorptest.MainApplication
import com.vitgames.softcorptest.R
import com.vitgames.softcorptest.databinding.ActivityMainBinding
import com.vitgames.softcorptest.domain.MainViewModel
import com.vitgames.softcorptest.domain.NetworkConnectionReceiver
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
        initNetworkConnectionReceiver()
        initProgressBar()
        handleDefaultRequest()
    }

    private fun initProgressBar() {
        viewModel.progressBarData.observe(this) { inProgress ->
            val visibility = if (inProgress) {
                View.VISIBLE
            } else View.GONE
            binding.progressBar.visibility = visibility
        }
    }

    private fun initNetworkConnectionReceiver() {
        val intentFilter =
            IntentFilter().apply { addAction(ConnectivityManager.CONNECTIVITY_ACTION) }
        registerReceiver(NetworkConnectionReceiver(viewModel), intentFilter)

        viewModel.networkConnectionData.observe(this) { isConnected ->
            if (!isConnected) {
                Snackbar.make(
                    binding.mainContainer,
                    "Check your network connection",
                    Snackbar.LENGTH_LONG
                )
                    .show();
            }
        }
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