package com.example.dailyconversor

import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val edtTaskName = findViewById<TextInputEditText>(R.id.tie_task_name)
        val tvResult = findViewById<TextView>(R.id.tv_result)
        val btnClean = findViewById<Button>(R.id.btn_clean)
        val btnCalculator = findViewById<Button>(R.id.btn_calculator)
        val tilNumber = findViewById<TextInputLayout>(R.id.textInputLayout)


        val firstSpinner = findViewById<Spinner>(R.id.convert_enter)
        val secondSpinner = findViewById<Spinner>(R.id.convert_exit)


        val categories = listOf("Weight", "Distance", "Volume")
        val dataMap = mapOf(
            "Weight" to listOf("Kilograms", "Grams", "Pounds"),
            "Distance" to listOf("Meters", "Kilometers", "Miles"),
            "Volume" to listOf("Liters", "Millimeters", "Gallons")
        )

        val categoryAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            categories
        )
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        firstSpinner.adapter = categoryAdapter

        var selectedUnit = ""


        firstSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedCategory = categories[position]
                val units = dataMap[selectedCategory] ?: emptyList()

                val unitAdapter = ArrayAdapter(
                    this@MainActivity,
                    android.R.layout.simple_spinner_item,
                    units
                )
                unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                secondSpinner.adapter = unitAdapter


                tvResult.text = "Result"

                secondSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        selectedUnit = units[position]
                        tvResult.text = "Result"
                    }


                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }


        }
        btnCalculator.setOnClickListener {
            val textResult = edtTaskName.text.toString()

            if (textResult.isNotEmpty()) {
                val value = textResult.toDoubleOrNull()
                if (value != null) {
                    val convertedValue = convertValue(value, selectedUnit)
                    tvResult.text = "Result: $convertedValue $selectedUnit"
                } else {
                    Snackbar.make(btnCalculator,"Invalid value",Snackbar.LENGTH_LONG).show()
                }
            } else {
                Snackbar.make(btnCalculator,"Enter a value",Snackbar.LENGTH_LONG).show()
            }

            true
        }

        btnClean.setOnClickListener {
            tvResult.text = ""
            edtTaskName.setText("")
            tilNumber.clearFocus()
        }


    }


    private fun convertValue(value: Double, unit: String): Double {
        return when (unit) {
            "Kilograms…" -> value * 1.0
            "Grams" -> value * 1000
            "Pounds" -> value * 2.20462
            "Meters" -> value * 1.0
            "Kilometers" -> value / 1000
            "Miles" -> value / 1609.34
            "Liters" -> value * 1.0
            "Millimeters" -> value * 1000
            "Gallons" -> value / 3.78541
            else -> value
        }
    }
}

