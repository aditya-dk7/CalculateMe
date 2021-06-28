package com.dk7aditya

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {
    private var lastNumeric : Boolean = false
    private var lastDot : Boolean = false
    private lateinit var tvView:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvView = findViewById(R.id.tvInput)
        lastNumeric = false
        lastDot = false
    }
    fun onDigit(view: View){
        tvView.append((view as Button).text)
        lastNumeric = true

    }
    fun onClear(view: View){
        tvView.text = ""
        lastNumeric = false
        lastDot = false
    }
    fun onDecimal(view: View){
        if(lastNumeric && !lastDot){
            tvView.append(".")
            lastNumeric = false
            lastDot = true
        }
    }
    fun onEquals(view: View){
        if(lastNumeric) {
            var expression: String = tvView.text.toString()
            var prefix = ""
            try{
                if(expression.startsWith("-")){
                    prefix = "-"
                    expression = expression.substring(1)
                }
                when {
                    expression.contains("-") -> {
                        val splitValue = expression.split("-")
                        var one = splitValue[0]
                        val two = splitValue[1]
                        if(prefix.isNotEmpty()){
                            one = prefix + one
                        }
                        tvView.text = simplifyExp((one.toDouble() - two.toDouble()).toString())

                    }
                    expression.contains("+") -> {
                        val splitValue = expression.split("+")
                        var one = splitValue[0]
                        val two = splitValue[1]
                        if(prefix.isNotEmpty()){
                            one = prefix + one
                        }
                        tvView.text = simplifyExp((one.toDouble() + two.toDouble()).toString())
                    }
                    expression.contains("x") -> {
                        val splitValue = expression.split("x")

                        var one = splitValue[0]
                        var two = splitValue[1]
                        if(prefix.isNotEmpty()){
                            one = prefix + one
                        }

                        tvView.text = simplifyExp((one.toDouble() * two.toDouble()).toString())
                    }
                    expression.contains("/") -> {
                        val splitValue = expression.split("/")
                        var one = splitValue[0]
                        val two = splitValue[1]
                        if(prefix.isNotEmpty()){
                            one = prefix + one
                        }
                        tvView.text = simplifyExp((one.toDouble() / two.toDouble()).toString())
                    }
                }
            }catch(e: ArithmeticException){
                e.printStackTrace()
            }
        }
    }
    fun onOperator(view: View){
        if(lastNumeric && !isOperatorAdded(tvView.text.toString())){
            tvView.append((view as Button).text)
            lastNumeric = false
            lastDot = false
        }
    }

    private fun isOperatorAdded(value: String) : Boolean{
        return if(value.startsWith("-")){
            false
        }else{
            value.contains("/") ||
                    value.contains("*") ||
                    value.contains("+") ||
                    value.contains("-")
        }
    }

    private fun simplifyExp(result: String): String{
        var value = result
        if(result.contains(".0")){
            value = result.substring(0, result.length - 2)
        }
        return value
    }
}