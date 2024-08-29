package com.example.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var displayInput: TextView
    private lateinit var displayResult: TextView
    private val input = StringBuilder() // to keep track of the current input
    private val evaluator = ExpressionEvaluator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the TextViews
        displayInput = findViewById(R.id.display_input)
        displayResult = findViewById(R.id.display_result)

        // Initialize buttons and set onClickListeners
        initializeButtons()
    }

    private fun initializeButtons() {
        // Number buttons
        findViewById<Button>(R.id.button0).setOnClickListener { appendInput("0") }
        findViewById<Button>(R.id.button1).setOnClickListener { appendInput("1") }
        findViewById<Button>(R.id.button2).setOnClickListener { appendInput("2") }
        findViewById<Button>(R.id.button3).setOnClickListener { appendInput("3") }
        findViewById<Button>(R.id.button4).setOnClickListener { appendInput("4") }
        findViewById<Button>(R.id.button5).setOnClickListener { appendInput("5") }
        findViewById<Button>(R.id.button6).setOnClickListener { appendInput("6") }
        findViewById<Button>(R.id.button7).setOnClickListener { appendInput("7") }
        findViewById<Button>(R.id.button8).setOnClickListener { appendInput("8") }
        findViewById<Button>(R.id.button9).setOnClickListener { appendInput("9") }

        // Operator buttons
        findViewById<Button>(R.id.buttonAdd).setOnClickListener { appendInput("+") }
        findViewById<Button>(R.id.buttonSubtract).setOnClickListener { appendInput("-") }
        findViewById<Button>(R.id.buttonMultiply).setOnClickListener { appendInput("*") }
        findViewById<Button>(R.id.buttonDivide).setOnClickListener { appendInput("/") }

        // Other buttons
        findViewById<Button>(R.id.buttonClear).setOnClickListener { clearInput() }
        findViewById<Button>(R.id.buttonBackspace).setOnClickListener { removeLastCharacter() }
        findViewById<Button>(R.id.buttonEqual).setOnClickListener { calculateResult() }
    }

//    fungsi untuk menambahkan karakter input
    private fun appendInput(value: String) {
        input.append(value)
        displayInput.text = input.toString()
    }

//    fungsi tombol C (Clear)
    private fun clearInput() {
        input.clear()
        displayInput.text = "0"
        displayResult.text = "0"
    }

//    fungsi tombol ← (backspace)
    private fun removeLastCharacter() {
        if (input.isNotEmpty()) {
            input.deleteCharAt(input.length - 1)
            displayInput.text = if (input.isNotEmpty()) input.toString() else "0"
        }
    }

//    fungsi tombol = (sama dengan)
    private fun calculateResult() {
        try {
            val expression = input.toString()
            val result = evaluator.evaluate(expression)
            displayResult.text = result.toString()
        } catch (e: Exception) {
            displayResult.text = "Error"
        }
    }
}
