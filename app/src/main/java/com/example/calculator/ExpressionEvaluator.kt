package com.example.calculator

import java.util.Stack

// Class yang berisi method untuk menghitung string
class ExpressionEvaluator {


    // Fungsi untuk memeriksa apakah operator 'op2' memiliki prioritas lebih tinggi atau sama dengan 'op1'
    // operator perkalian atau pembagian akan dihitung lebih dulu daripada pengurangan dan penjumlahan
    private fun checkPriority(op1: Char, op2: Char): Boolean {
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')) return false
        return true
    }


    // Fungsi untuk menerapkan operator pada dua angka dan mengembalikan hasilnya
    private fun applyOperation(op: Char, b: Double, a: Double): Double {
        return when (op) {
            '+' -> a + b
            '-' -> a - b
            '*' -> a * b
            '/' -> {
                if (b == 0.0) throw ArithmeticException("Division by zero")
                a / b
            }
            else -> 0.0
        }
    }


    // Fungsi utama untuk mengevaluasi ekspresi matematika
    fun evaluate(expression: String): String {
        // Stack untuk menyimpan angka
        val numbers = Stack<Double>()
        // Stack untuk menyimpan operator (+, -, *, /)
        val operators = Stack<Char>()

        var i = 0
        while (i < expression.length) {
            val c = expression[i]

            // Jika karakter saat ini adalah spasi, lewati
            if (c == ' ') {
                i++
                continue
            }

            // Jika karakter saat ini adalah angka (termasuk desimal)
            if (c in '0'..'9' || c == '.') {
                val sb = StringBuilder()
                // Ambil seluruh angka (termasuk bagian desimal)
                while (i < expression.length && (expression[i] in '0'..'9' || expression[i] == '.')) {
                    sb.append(expression[i++])
                }
                // Masukkan angka ke dalam stack
                numbers.push(sb.toString().toDouble())
                i--
            }
            // Jika karakter saat ini adalah operator
            else if (c in "+-*/") {
                // Proses semua operator dengan prioritas lebih tinggi atau sama
                while (operators.isNotEmpty() && checkPriority(c, operators.peek())) {
                    numbers.push(applyOperation(operators.pop(), numbers.pop(), numbers.pop()))
                }
                // Masukkan operator saat ini ke dalam stack
                operators.push(c)
            }
            i++
        }

        // Proses semua operator yang tersisa didalam stack
        while (operators.isNotEmpty()) {
            numbers.push(applyOperation(operators.pop(), numbers.pop(), numbers.pop()))
        }

        // Nilai akhir dari ekspresi
        val result = numbers.pop()

        // Periksa apakah hasilnya bilangan bulat atau bukan
        return if (result == result.toInt().toDouble()) {
            result.toInt().toString()  // Kembalikan sebagai bilangan bulat
        } else {
            result.toString()  // Tetap sebagai float
        }
    }

}
