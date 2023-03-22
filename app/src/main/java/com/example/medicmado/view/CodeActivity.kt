package com.example.medicmado.view

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.ViewModelProvider
import com.example.medicmado.ui.theme.MedicMADOTheme
import com.example.medicmado.ui.components.AppBackButton
import com.example.medicmado.ui.components.AppTextField
import com.example.medicmado.ui.theme.descriptionColor
import com.example.medicmado.viewmodel.LoginViewModel
import kotlinx.coroutines.delay

/*
Описание: Класс экрана проверки кода
Дата создания: 22.03.2023 12:45
Автор: Георгий Хасанов
*/
class CodeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MedicMADOTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    CodeContent()
                }
            }
        }
    }

    /*
    Описание: Контент экрана проверки кода
    Дата создания: 22.03.2023 12:45
    Автор: Георгий Хасанов
    */
    @Composable
    fun CodeContent() {
        val mContext = LocalContext.current
        val viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        val sharedPreferences = this.getSharedPreferences("shared", Context.MODE_PRIVATE)

        var timeLeft by rememberSaveable { mutableStateOf(60) }

        var code1 by rememberSaveable { mutableStateOf("") }
        var code2 by rememberSaveable { mutableStateOf("") }
        var code3 by rememberSaveable { mutableStateOf("") }
        var code4 by rememberSaveable { mutableStateOf("") }

        var isAlertVisible by rememberSaveable { mutableStateOf(false) }

        var isLoading by rememberSaveable { mutableStateOf(false) }

        LaunchedEffect(timeLeft) {
            delay(1000)

            if (timeLeft > 0) {
                timeLeft -= 1
            } else {
                timeLeft = 60
                viewModel.sendEmailCode(intent.getStringExtra("email").toString())
            }
        }

        val token by viewModel.token.observeAsState()
        LaunchedEffect(token) {
            if (token != null) {
                with(sharedPreferences.edit()) {
                    putString("token", token)
                    apply()
                }
            }

            isLoading = false
        }

        val errorMessage by viewModel.errorMessage.observeAsState()
        LaunchedEffect(errorMessage) {
            if (errorMessage != null) {
                isAlertVisible = true
            }

            isLoading = false
        }

        Scaffold(
            topBar = {
                Row(modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(top = 24.dp)) {
                    AppBackButton {
                        onBackPressed()
                    }
                }
            }
        ) { padding ->
            Box(Modifier.fillMaxSize().padding(padding)) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                        .align(Alignment.Center)
                ) {
                    Text(
                        "Введите код из E-mail",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.W600,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(28.dp))
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                        .fillMaxWidth()
                    ) {
                        AppTextField(
                            value = code1,
                            onValueChange = {
                                if (it.length <= 1) {
                                    code1 = it
                                }
                            },
                            modifier = Modifier.size(48.dp),
                            singleLine = true,
                            textStyle = TextStyle(textAlign = TextAlign.Center, lineHeight = 0.sp, fontSize = 20.sp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        AppTextField(
                            value = code2,
                            onValueChange = {
                                if (it.length <= 1) {
                                    code2 = it
                                }
                            },
                            modifier = Modifier.size(48.dp),
                            singleLine = true,
                            textStyle = TextStyle(textAlign = TextAlign.Center, lineHeight = 0.sp, fontSize = 20.sp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        AppTextField(
                            value = code3,
                            onValueChange = {
                                if (it.length <= 1) {
                                    code3 = it
                                }
                            },
                            modifier = Modifier.size(48.dp),
                            singleLine = true,
                            textStyle = TextStyle(textAlign = TextAlign.Center, lineHeight = 0.sp, fontSize = 20.sp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        AppTextField(
                            value = code4,
                            onValueChange = {
                                if (it.length <= 1) {
                                    code4 = it
                                }

                                if (it.length == 1) {
                                    viewModel.checkEmailCode(intent.getStringExtra("email").toString(), "$code1$code2$code3$code4")
                                    isLoading = true
                                }
                            },
                            modifier = Modifier.size(48.dp),
                            singleLine = true,
                            textStyle = TextStyle(textAlign = TextAlign.Center, lineHeight = 0.sp, fontSize = 20.sp)
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Отправить код повторно можно будет через $timeLeft секунд",
                        fontSize = 15.sp,
                        color = descriptionColor,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.widthIn(max = 300.dp).fillMaxWidth()
                    )
                }
            }
        }

        if (isLoading) {
            Dialog(onDismissRequest = {}) {
                CircularProgressIndicator()
            }
        }

        if (isAlertVisible) {
            AlertDialog(
                onDismissRequest = { isAlertVisible = false },
                title = { Text(text = "Ошибка") },
                text = { Text(text = viewModel.errorMessage.value.toString() ) },
                buttons = {
                    TextButton(
                        onClick = { isAlertVisible = false }
                    ) {
                        Text(text = "OK")
                    }
                }
            )
        }
    }
}