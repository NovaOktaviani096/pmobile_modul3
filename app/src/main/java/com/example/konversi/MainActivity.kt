package com.example.konversi

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.konversi.databinding.KonversiBinding
import java.text.NumberFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: KonversiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = KonversiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calculateButton.setOnClickListener { calculate() }

        binding.nilaiMatauang.setOnKeyListener { view, keyCode, _ -> handleKeyEvent(view, keyCode) }
    }

    private fun calculate() {
        val stringInTextField = binding.nilaiMatauang.text.toString()
        val cost = stringInTextField.toDoubleOrNull()
        if (cost == null) {
            binding.result.text = ""
            return
        }

        val kursOptions = when (binding.options.checkedRadioButtonId) {
            R.id.euro -> 15622.55
            R.id.dollar -> 14363.25
            R.id.yen -> 114.43
            else -> 3829.94
        }

        var cash = kursOptions * cost

        val indonesianLocale = Locale("in", "ID")
        val formattedResult = NumberFormat.getCurrencyInstance(indonesianLocale).format(cash)
        binding.result.text = getString(R.string.cashAmount, formattedResult)
    }

    private fun handleKeyEvent(view: View, keyCode:Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER){
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}