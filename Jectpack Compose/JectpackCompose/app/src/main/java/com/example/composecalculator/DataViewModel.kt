package com.example.composecalculator

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf

class DataViewModel : ViewModel() {
    var firstNumber by mutableStateOf("")
    var secondNumber by mutableStateOf("")
    var result by mutableStateOf("")

    var showResult by mutableStateOf(false)

    fun add()
    {
        val f1 = firstNumber.toDoubleOrNull()
        val f2 = secondNumber.toDoubleOrNull()
        if (f1 != null && f2 != null)
        {
            result = (f1 + f2).toString()
            showResult =true
        }
    }

    fun minus()
    {
        val f1 = firstNumber.toDoubleOrNull()
        val f2 = secondNumber.toDoubleOrNull()
        if (f1 != null && f2 != null)
        {
            result = (f1 - f2).toString()
            showResult =true
        }
    }

    fun multiply()
    {
        val f1 = firstNumber.toDoubleOrNull()
        val f2 = secondNumber.toDoubleOrNull()
        if (f1 != null && f2 != null)
        {
            result = (f1 * f2).toString()
            showResult =true
        }
    }

    fun division(context: Context)
    {
        val f1 = firstNumber.toDoubleOrNull()
        val f2 = secondNumber.toDoubleOrNull()
        if (f1 != null && f2 != null && f2 != 0.0)
        {
            result = (f1 / f2).toString()
            showResult =true
        }
        else
        {
            result = ("Error").toString()
            showResult = true
            Toast.makeText(context, "Cannot divide by 0", Toast.LENGTH_SHORT).show()
        }
    }

    fun reset()
    {
        showResult = false
    }
}