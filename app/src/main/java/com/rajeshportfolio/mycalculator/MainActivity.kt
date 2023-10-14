package com.rajeshportfolio.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
class MainActivity : AppCompatActivity() {
    private var tvResult : TextView? = null
    private var lastNumeric : Boolean = false
    private var lastDot : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvResult = findViewById(R.id.tvResult)
    }

    fun onOperator(view : View) {
        if (tvResult?.text.toString().isEmpty()) {
            if (view is Button) {
                if(view.text == "-") {
                    tvResult?.append(view.text)
                }
            }
        }else {
            if (lastNumeric && !isOperatorAdded(tvResult?.text.toString())) {
                tvResult?.append((view as Button).text)
                lastNumeric = false
                lastDot = false
            }
        }
    }
    private fun isOperatorAdded(value : String) : Boolean {
        return if (value.startsWith("-")) {
            val data = value.substring(1)
            data.contains("/") || data.contains("*") || data.contains("+") || data.contains("-")
        } else {
            value.contains("/") || value.contains("*") || value.contains("+") || value.contains("-")
        }
    }
    fun onDigit(view : View) {
        tvResult?.append((view as Button).text)
        lastNumeric = true
    }
    fun onClear(view : View) {
        tvResult?.text = ""
        lastNumeric = false
        lastDot = false
    }

    fun onDecimalPoint(view: View) {
        if (lastNumeric && !lastDot) {
            tvResult?.append(".")
            lastNumeric = false
            lastDot = true
        }
    }
    fun onEqual(view: View) {
        if (lastNumeric) {
            var tvValue = tvResult?.text.toString()
            var prefix = ""
            try {
                if (tvValue.startsWith("-")) {
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }
                if (tvValue.contains("-")) {
                    val splitValue = tvValue.split("-")
                    var one = splitValue[0]
                    val two = splitValue[1]
                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }
                    tvResult?.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                } else if (tvValue.contains("+")) {
                    val splitValue = tvValue.split("+")
                    var one = splitValue[0]
                    val two = splitValue[1]
                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }
                    tvResult?.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                } else if (tvValue.contains("*")) {
                    val splitValue = tvValue.split("*")
                    var one = splitValue[0]
                    val two = splitValue[1]
                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }
                    tvResult?.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                } else if (tvValue.contains("/")) {
                    val splitValue = tvValue.split("/")
                    var one = splitValue[0]
                    val two = splitValue[1]
                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }
                    tvResult?.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                }
            } catch (e : ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

    fun removeElement(view: View) {
        if (tvResult?.text.toString().isNotEmpty()) {
            tvResult?.text = tvResult?.text.toString().substring(0, tvResult?.text.toString().length - 1)
        }
    }

    private fun removeZeroAfterDot(result : String) : String {
        var value = result
        if (result.contains(".0")) {
            value = result.substring(0, result.length - 2)
        }
        return value
    }
}