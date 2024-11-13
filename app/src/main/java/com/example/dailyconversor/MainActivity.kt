package com.example.dailyconversor

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText



class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val edtTaskName = findViewById<TextInputEditText>(R.id.tie_task_name)
        val tvResult = findViewById<TextView>(R.id.tv_result)
        val btnClean = findViewById<Button>(R.id.btn_clean)
        val btnCalculator = findViewById<Button>(R.id.btn_calculator)

        btnCalculator.setOnClickListener {

             val taskNamestr : String = edtTaskName.text.toString()

                if (taskNamestr.isEmpty() == true) {
                Snackbar.make(btnCalculator, "Preencha o campo de número", Snackbar.LENGTH_LONG)
                    .show()
            }else{
                tvResult.text = taskNamestr
            }
        }
    }
}
