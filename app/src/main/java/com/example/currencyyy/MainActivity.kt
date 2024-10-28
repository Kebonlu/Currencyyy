package com.example.currencyyy

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var amountEditText: EditText
    private lateinit var fromCurrencySpinner: Spinner
    private lateinit var toCurrencySpinner: Spinner
    private lateinit var convertButton: Button
    private lateinit var resultTextView: TextView

    // Tỉ giá hối đoái (giả định)
    private val exchangeRates = mapOf(
        "VND" to 1.0,       // 1 VND = 1 VND
        "USD" to 24000.0,   // 1 USD = 24000 VND
        "GBP" to 32000.0,   // 1 Bảng Anh = 32000 VND
        "CNY" to 3500.0,    // 1 Nhân dân tệ = 3500 VND
        "JPY" to 150.0      // 1 Yên = 150 VND
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        amountEditText = findViewById(R.id.amountEditText)
        fromCurrencySpinner = findViewById(R.id.fromCurrencySpinner)
        toCurrencySpinner = findViewById(R.id.toCurrencySpinner)
        convertButton = findViewById(R.id.convertButton)
        resultTextView = findViewById(R.id.resultTextView)

        // Thiết lập Spinner với các loại tiền tệ
        val currencies = exchangeRates.keys.toList()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        fromCurrencySpinner.adapter = adapter
        toCurrencySpinner.adapter = adapter

        convertButton.setOnClickListener { convertCurrency() }
    }

    private fun convertCurrency() {
        val amountString = amountEditText.text.toString()
        if (amountString.isNotEmpty()) {
            val amount = amountString.toDouble()
            val fromCurrency = fromCurrencySpinner.selectedItem.toString()
            val toCurrency = toCurrencySpinner.selectedItem.toString()

            // Tính toán chuyển đổi
            val amountInVND = amount * (exchangeRates[fromCurrency] ?: 0.0)
            val convertedAmount = amountInVND / (exchangeRates[toCurrency] ?: 1.0)

            resultTextView.text = String.format("Số tiền chuyển đổi: %.2f %s", convertedAmount, toCurrency)
        } else {
            resultTextView.text = "Vui lòng nhập số tiền."
        }
    }
}
