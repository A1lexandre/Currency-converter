package com.android.currencyconverter

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.android.currencyconverter.data.ui.ConversionEvent
import com.android.currencyconverter.databinding.ActivityMainBinding
import com.android.currencyconverter.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.math.round

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
            if(amount.isNotEmpty() && from.isNotEmpty() && to.isNotEmpty()) {
                dismissKeyBoard()
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
                            binding.tvResult.text = "Resultado: ${formatResult(event.reponse.result)}\n " +
                                    "                               data: ${formatDate(event.reponse.date)}"
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

    private fun formatResult(result: Double): String {
        return (round(result * 100) / 100).toString()
    }
    
    private fun formatDate(date: String): String {
        val split = date.split("-")
        return "${split[2]}/${split[1]}/${split[0]}"
    }

    private fun dismissKeyBoard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (currentFocus != null)
            imm.hideSoftInputFromWindow(this.currentFocus?.applicationWindowToken, 0)
    }
 }