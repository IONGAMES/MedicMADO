package com.example.medicmado.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.medicmado.ui.theme.MedicMADOTheme

/*
Описание: Класс экрана входа в аккаунт
Дата создания: 22.03.2023 10:05
Автор: Георгий Хасанов
*/
class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MedicMADOTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    LoginContent()
                }
            }
        }
    }

    /*
    Описание: Контент экрана входа
    Дата создания: 22.03.2023 10:05
    Автор: Георгий Хасанов
    */
    fun LoginContent() {

    }
}