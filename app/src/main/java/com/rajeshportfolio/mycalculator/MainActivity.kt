package com.rajeshportfolio.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private var tvResult : TextView? = null
    var lastNumeric : Boolean = false
    var lastDot : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var a : String = ""
        var operator : String = ""
        var b : String = ""

        tvResult= findViewById(R.id.tvResult)
    }

    fun onDigit(view: View) {
        tvResult?.append((view as Button).text)
        lastNumeric = true
        lastDot = false
    }

    fun  onClear(view: View) {
        tvResult?.text = ""
    }

    fun onDecimalPoint(view: View) {
        if (lastNumeric && !lastDot) {
            tvResult?.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onOperator(view : View) {
        tvResult?.text?.let {
            if (lastNumeric && !isOperatorAdded(it.toString())) {
                tvResult?.append((view as Button).text)
                lastNumeric = false
                lastDot = false
            }
        }
    }

    fun onEqual(view: View) {
        if (lastNumeric) {
            var tvValue = tvResult?.text.toString()
            var one : Double = 0.0
            var two : Double = 0.0
            try {
//                val splitValue = tvValue.split("+", "-", "/", "*")
//                spilt the value except the first character if it is a negative number

                val splitValue = when {
                    tvValue.startsWith("-") -> {
                        tvValue.substring(1).split("+", "-", "/", "*")

                    }
                    else -> {
                        tvValue.split("+", "-", "/", "*")
                    }
                }

                var one = when {
                    splitValue[0].startsWith("-") -> {
                        splitValue[0].substring(1).toDouble() * -1
                    }
                    else -> {
                        splitValue[0].toDouble()
                    }
                }

                var two = when {
                    splitValue[1].startsWith("-") -> {
                        splitValue[1].substring(1).toDouble() * -1
                    }
                    else -> {
                        splitValue[1].toDouble()
                    }
                }


//                perform the operation
                when {
                    tvValue.contains("+") -> {
                        val result = one + two
                        tvResult?.text = result.toString()
                    }

                    tvValue.contains("-") -> {
                        val result = one - two
                        tvResult?.text = result.toString()
                    }

                    tvValue.contains("*") -> {
                        val result = one * two
                        tvResult?.text = result.toString()
                    }

                    tvValue.contains("/") -> {
                        val result = one / two
                        tvResult?.text = result.toString()
                    }
                }

            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

    private fun isOperatorAdded(value : String) : Boolean {
        return if(value.startsWith("-")) {
            false
        } else {
            value.contains("/") || value.contains("*") || value.contains("+") || value.contains("-")
        }
    }

}