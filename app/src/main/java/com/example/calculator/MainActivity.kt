package com.example.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var displayInput: TextView
    private lateinit var displayResult: TextView
    private val input = StringBuilder()
    private val evaluator = ExpressionEvaluator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the TextViews
        displayInput = findViewById(R.id.display_input)
        displayResult = findViewById(R.id.display_result)

        // Initialize buttons and set onClickListeners
        assignButtons()
    }

    //assign tombol dengan fungsinya
    private fun assignButtons() {
        // Tombol angka
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

        // Tombol Operator
        findViewById<Button>(R.id.buttonAdd).setOnClickListener { appendInput("+") }
        findViewById<Button>(R.id.buttonSubtract).setOnClickListener { appendInput("-") }
        findViewById<Button>(R.id.buttonMultiply).setOnClickListener { appendInput("*") }
        findViewById<Button>(R.id.buttonDivide).setOnClickListener { appendInput("/") }

        // Tombol lain
        findViewById<Button>(R.id.buttonClear).setOnClickListener { clearInput() }
        findViewById<Button>(R.id.buttonBackspace).setOnClickListener { removeLastCharacter() }
        findViewById<Button>(R.id.buttonEqual).setOnClickListener { calculate() }
    }

    // Fungsi untuk menambahkan karakter input
    private fun appendInput(value: String) {

        if (input.isEmpty() && value in "+-*/") {
            input.append("0")
        }
        input.append(value)
        displayInput.text = input.toString()
    }

    // Fungsi tombol C (Clear)
    private fun clearInput() {
        input.clear()
        displayInput.text = "0"
        displayResult.text = "0"
    }

    // Fungsi tombol ← (backspace)
    private fun removeLastCharacter() {
        if (input.isNotEmpty()) {
            input.deleteCharAt(input.length - 1)
            displayInput.text = if (input.isNotEmpty()) input.toString() else "0"
        }
    }

    // Fungsi tombol = (sama dengan)
    private fun calculate() {
        try {
            val expression = input.toString()

            // fungsi evaluate dari class ExpressionEvaluator
            val result = evaluator.evaluate(expression)
            displayResult.text = result.toString()


        } catch (e: ArithmeticException) {
            // TOAST error jika dibagi 0
            displayResult.text = "Error"
            Toast.makeText(this, "Error: Division by zero", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            // TOAST error yang lain
            displayResult.text = "Error"
            Toast.makeText(this, "Error: Invalid operation", Toast.LENGTH_SHORT).show()
        }
    }
}
