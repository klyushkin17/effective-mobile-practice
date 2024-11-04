package com.example.kitlin_problem_3

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.kitlin_problem_3.data.ListOfTypes
import com.example.kitlin_problem_3.extantion_functoins.findFirstIntInList
import com.example.kitlin_problem_3.ui.theme.Kitlinproblem3Theme

const val TAG = "Find first int"

class MainActivity : ComponentActivity() {
    private lateinit var listOfTypes: List<Any>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Kitlinproblem3Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(
                            onClick = {
                                listOfTypes = ListOfTypes.listOfTypes
                                Log.d(
                                    TAG,
                                    "The first Int type in the list is ${listOfTypes.findFirstIntInList()}"
                                )
                            }
                        ) {
                            Text(text = "Start script")
                        }
                    }
                }
            }
        }
    }
}
