package com.example.currencyyy

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    lateinit var inputAmount: EditText
    lateinit var fromCurrency: Spinner
    lateinit var toCurrency: Spinner
    lateinit var convertButton: Button
    lateinit var resultText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputAmount = findViewById(R.id.inputAmount)
        fromCurrency = findViewById(R.id.fromCurrency)
        toCurrency = findViewById(R.id.toCurrency)
        convertButton = findViewById(R.id.convertButton)
        resultText = findViewById(R.id.resultText)

        val currencies = arrayOf("VND", "USD", "GBP", "CNY", "JPY")
        fromCurrency.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, currencies)
        toCurrency.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, currencies)

        convertButton.setOnClickListener {
            convertCurrency()
        }
    }

    private fun convertCurrency() {
        val amount = inputAmount.text.toString().toDoubleOrNull()
        if (amount == null) {
            Toast.makeText(this, "Vui lòng nhập số tiền hợp lệ", Toast.LENGTH_SHORT).show()
            return
        }

        val from = fromCurrency.selectedItem.toString()
        val to = toCurrency.selectedItem.toString()

        val result = convertAmount(amount, from, to)
        resultText.text = "Kết quả: $result $to"
    }

    private fun convertAmount(amount: Double, from: String, to: String): Double {
        val exchangeRates = mapOf(
            "USD" to 1.0,
            "VND" to 24000.0,
            "GBP" to 0.74,
            "CNY" to 7.2,
            "JPY" to 110.0
        )

        val fromRate = exchangeRates[from] ?: return 0.0
        val toRate = exchangeRates[to] ?: return 0.0

        // Chuyển đổi số tiền từ đồng tiền "from" sang USD trước, sau đó chuyển từ USD sang "to".
        return amount / fromRate * toRate
    }
}
