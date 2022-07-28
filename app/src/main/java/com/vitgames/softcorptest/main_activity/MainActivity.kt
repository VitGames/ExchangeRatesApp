package com.vitgames.softcorptest.main_activity

import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.vitgames.softcorptest.MainApplication
import com.vitgames.softcorptest.R
import com.vitgames.softcorptest.databinding.ActivityMainBinding
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
        initAmountSpinner()
        initBottomMenu()
        handleDefaultRequest()

        binding.sortButton.setOnClickListener {
            // TODO(Баг - нажатие на боттом меню не вызывает другие фрагменты)
            findNavController(R.id.fragment).navigate(R.id.action_global_sortedFragment)
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

    private fun initAmountSpinner() {
        ArrayAdapter.createFromResource(
            this, R.array.amount_array, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.amountSpinner.adapter = adapter
        }

        binding.amountSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parentView: AdapterView<*>?,
                    selectedItemView: View?,
                    position: Int,
                    id: Long
                ) {
                    val newAmount = (selectedItemView as TextView).text.toString()
                    viewModel.setAmount(newAmount)
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