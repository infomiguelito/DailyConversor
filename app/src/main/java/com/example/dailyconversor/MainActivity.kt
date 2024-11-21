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


        val categories = listOf("Peso", "Distância", "Volume")
        val dataMap = mapOf(
            "Peso" to listOf("Quilogramas", "Gramas", "Libras"),
            "Distância" to listOf("Metros", "Quilômetros", "Milhas"),
            "Volume" to listOf("Litros", "Mililitros", "Galões")
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


                tvResult.text = "Resultado"

                secondSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        selectedUnit = units[position]
                        tvResult.text = "Resultado"
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
                    tvResult.text = "Resultado: $convertedValue $selectedUnit"
                } else {
                    Snackbar.make(btnCalculator,"Valor inválido",Snackbar.LENGTH_LONG).show()
                }
            } else {
                Snackbar.make(btnCalculator,"Insira um valor",Snackbar.LENGTH_LONG).show()
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
            "Quilogramas" -> value * 1.0
            "Gramas" -> value * 1000
            "Libras" -> value * 2.20462
            "Metros" -> value * 1.0
            "Quilômetros" -> value / 1000
            "Milhas" -> value / 1609.34
            "Litros" -> value * 1.0
            "Mililitros" -> value * 1000
            "Galões" -> value / 3.78541
            else -> value
        }
    }
}

