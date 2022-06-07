package com.android.currencyconverter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.android.currencyconverter.data.ui.ConversionEvent
import com.android.currencyconverter.databinding.ActivityMainBinding
import com.android.currencyconverter.main.MainViewModel
import com.android.currencyconverter.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnConversion.setOnClickListener {
            val amount = binding.tidAmount.text.toString()
            val from =   binding.spinFrom.selectedItem.toString()
            val to = binding.spinTo.selectedItem.toString()
            if(!amount.isNullOrEmpty() && !from.isNullOrEmpty() && !to.isNullOrEmpty()) {
                viewModel.convert(amount, from, to)
            }
        }
        setupListener()
    }

    private fun setupListener() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.convertionState.collect { event ->
                    when(event) {
                        is ConversionEvent.Loading -> binding.pbConversion.visibility = View.VISIBLE
                        is ConversionEvent.Success -> {
                            binding.pbConversion.visibility = View.GONE
                            binding.tvResult.text = event.reponse.result.toString()
                        }
                        is ConversionEvent.Failure -> {
                            binding.pbConversion.visibility = View.GONE
                            Toast.makeText(this@MainActivity, event.message, Toast.LENGTH_SHORT).show()
                        }

                    }
                }
            }
        }
    }
}