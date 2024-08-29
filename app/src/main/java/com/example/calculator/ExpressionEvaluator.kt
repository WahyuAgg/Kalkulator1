package com.example.calculator

class ExpressionEvaluator {

    // Fungsi utama untuk mengevaluasi ekspresi matematika dalam bentuk string
    fun evaluate(expression: String): Double {
        // Menghapus spasi dari ekspresi untuk memudahkan parsing
        val cleanedExpression = expression.replace(" ", "")
        return parseExpression(cleanedExpression)
    }

    // Fungsi untuk mem-parsing ekspresi yang mungkin mengandung tanda kurung
    private fun parseExpression(expression: String): Double {
        var exp = expression
        // Memeriksa dan memproses tanda kurung terlebih dahulu
        while (exp.contains("(")) {
            // Menemukan indeks tanda kurung tutup pertama
            val closingIndex = exp.indexOf(")")
            var openingIndex = closingIndex
            // Menemukan indeks tanda kurung buka yang sesuai
            while (openingIndex > 0 && exp[openingIndex] != '(') {
                openingIndex--
            }
            // Mengambil sub-ekspresi di dalam tanda kurung
            val subExpression = exp.substring(openingIndex + 1, closingIndex)
            // Mengevaluasi sub-ekspresi dan menggantikannya dalam ekspresi utama
            val result = parseSimpleExpression(subExpression)
            exp = exp.substring(0, openingIndex) + result + exp.substring(closingIndex + 1)
        }
        // Mengevaluasi ekspresi tanpa tanda kurung
        return parseSimpleExpression(exp)
    }

    // Fungsi untuk mem-parsing ekspresi sederhana tanpa tanda kurung
    private fun parseSimpleExpression(expression: String): Double {
        var exp = expression
        // Memproses perkalian dan pembagian terlebih dahulu
        while (exp.contains("*") || exp.contains("/")) {
            val operators = listOf("*", "/")
            for (operator in operators) {
                val index = exp.indexOf(operator)
                if (index != -1) {
                    // Memisahkan operand kiri dan kanan berdasarkan operator
                    val leftOperand = exp.substring(0, index).trim()
                    val rightOperand = exp.substring(index + 1).trim()
                    // Mengonversi operand menjadi nilai numerik
                    val leftValue = if (leftOperand.isEmpty()) 0.0 else parseSimpleOperand(leftOperand)
                    val rightValue = parseSimpleOperand(rightOperand)
                    // Menghitung hasil berdasarkan operator (perkalian atau pembagian)
                    val result = if (operator == "*") leftValue * rightValue else leftValue / rightValue
                    // Mengganti bagian ekspresi dengan hasil kalkulasi
                    exp = "$result${exp.substring(index + rightOperand.length + 1)}"
                    break
                }
            }
        }
        // Memproses penjumlahan dan pengurangan
        while (exp.contains("+") || exp.contains("-")) {
            val operators = listOf("+", "-")
            for (operator in operators) {
                val index = exp.indexOf(operator)
                if (index != -1) {
                    // Memisahkan operand kiri dan kanan berdasarkan operator
                    val leftOperand = exp.substring(0, index).trim()
                    val rightOperand = exp.substring(index + 1).trim()
                    // Mengonversi operand menjadi nilai numerik
                    val leftValue = if (leftOperand.isEmpty()) 0.0 else parseSimpleOperand(leftOperand)
                    val rightValue = parseSimpleOperand(rightOperand)
                    // Menghitung hasil berdasarkan operator (penjumlahan atau pengurangan)
                    val result = if (operator == "+") leftValue + rightValue else leftValue - rightValue
                    // Mengganti bagian ekspresi dengan hasil kalkulasi
                    exp = "$result${exp.substring(index + rightOperand.length + 1)}"
                    break
                }
            }
        }
        // Mengembalikan hasil akhir sebagai nilai double
        return exp.toDouble()
    }

    // Fungsi untuk mengonversi operand menjadi nilai numerik
    private fun parseSimpleOperand(operand: String): Double {
        // Jika konversi gagal, kembalikan 0.0 sebagai default
        return operand.toDoubleOrNull() ?: 0.0
    }
}
