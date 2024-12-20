package com.example.composecalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composecalculator.ui.theme.ComposeCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeCalculatorTheme {
                val dataViewModel: DataViewModel = DataViewModel()
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.DarkGray),
                    contentAlignment = Alignment.Center
                ) {
                    MainScreen(dataViewModel = dataViewModel)
                }
            }
        }
    }
}

@Composable
fun InputTaking(dataViewModel: DataViewModel)
{
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray)
    ) {
        TextField(
            value = dataViewModel.firstNumber,
            onValueChange = {text -> dataViewModel.firstNumber = text
                dataViewModel.reset()
            },
            label = {Text("First Number")},
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
        )

        TextField(
            value = dataViewModel.secondNumber,
            onValueChange = { text ->
                dataViewModel.secondNumber = text
                dataViewModel.reset()
            },
            label = {Text("Second Number")},
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
        )
    }
}

@Preview
@Composable
fun InputTakingView()
{
    val dataViewModel:DataViewModel = DataViewModel()
    InputTaking(dataViewModel = dataViewModel)
}

@Composable
fun AddButton(dataViewModel: DataViewModel) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .background(Color.LightGray), horizontalArrangement = Arrangement.Center) {
        Button(onClick = { dataViewModel.add() }) {
            Icon(
                Icons.Rounded.Add,
                contentDescription = "Add")
        }
    }
}

@Preview
@Composable
fun AddButtonView()
{
    val dataViewModel:DataViewModel = DataViewModel()
    AddButton(dataViewModel = dataViewModel)
}

@Composable
fun MinusButton(dataViewModel: DataViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray), horizontalArrangement = Arrangement.Center
    ) {
        Button(onClick = { dataViewModel.minus() })
        {
            Icon(painter = painterResource(id = R.drawable.minus),
                contentDescription = "Divide")
        }
    }
}

@Preview
@Composable
fun MinusButtonView()
{
    val dataViewModel:DataViewModel = DataViewModel()
    MinusButton(dataViewModel = dataViewModel)
}

@Composable
fun MultiplyButton(dataViewModel: DataViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray), horizontalArrangement = Arrangement.Center
    ) {
        Button(onClick = { dataViewModel.multiply() }) {
            Icon(
                Icons.Rounded.Clear,
                contentDescription = "Multiply")
        }
    }
}

@Preview
@Composable
fun MultiplyButtonView()
{
    val dataViewModel:DataViewModel = DataViewModel()
    MultiplyButton(dataViewModel = dataViewModel)
}

@Composable
fun DivideButton(dataViewModel: DataViewModel)
{
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray), horizontalArrangement = Arrangement.Center
    ) {
        Button(onClick = { dataViewModel.division(context) })
        {
            Text(text = "/")
        }
    }
}

@Preview
@Composable
fun DivideButtonView()
{
    val dataViewModel:DataViewModel = DataViewModel()
    DivideButton(dataViewModel = dataViewModel)
}

@Composable
fun ShowResult(dataViewModel: DataViewModel) {
    if(dataViewModel.showResult){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray),
            horizontalArrangement = Arrangement.Center
        )
        {
            Text(
                text = "The result is ${dataViewModel.result}",
                style = TextStyle(fontSize = 18.sp)
            )
        }
    }
}

@Preview
@Composable
fun ShowResultView()
{
    val dataViewModel:DataViewModel = DataViewModel()
    ShowResult(dataViewModel = dataViewModel)
}

@Composable
fun MainScreen(dataViewModel: DataViewModel)
{
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray), verticalArrangement = Arrangement.Center){

        InputTaking(dataViewModel = dataViewModel)
        AddButton(dataViewModel = dataViewModel)
        MinusButton(dataViewModel = dataViewModel)
        MultiplyButton(dataViewModel = dataViewModel)
        DivideButton(dataViewModel = dataViewModel)
        Spacer(modifier = Modifier.height(16.dp))
        ShowResult(dataViewModel = dataViewModel)
    }
}

@Preview
@Composable
fun MainScreenView()
{
    val dataViewModel:DataViewModel = DataViewModel()
    MainScreen(dataViewModel = dataViewModel)
}
