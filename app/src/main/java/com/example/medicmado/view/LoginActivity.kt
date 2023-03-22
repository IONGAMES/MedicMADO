package com.example.medicmado.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.ViewModelProvider
import com.example.medicmado.ui.theme.MedicMADOTheme
import com.example.medicmado.R
import com.example.medicmado.ui.components.AppButton
import com.example.medicmado.ui.components.AppTextField
import com.example.medicmado.ui.theme.descriptionColor
import com.example.medicmado.ui.theme.inputColor
import com.example.medicmado.ui.theme.textColor
import com.example.medicmado.viewmodel.LoginViewModel

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
    @Composable
    fun LoginContent() {
        val mContext = LocalContext.current
        val viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        var emailText by rememberSaveable { mutableStateOf("") }

        var isEnabled by rememberSaveable { mutableStateOf(false) }
        var isAlertVisible by rememberSaveable { mutableStateOf(false) }

        var isLoading by rememberSaveable { mutableStateOf(false) }

        val response by viewModel.responseCode.observeAsState()
        LaunchedEffect(response) {
            if (response == 200) {
                val intent = Intent(mContext, CodeActivity::class.java)
                intent.putExtra("email", emailText)
                startActivity(intent)
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 62.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.ic_emojies),
                    contentDescription = "",
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Добро пожаловать!",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.W700
                )
            }
            Spacer(modifier = Modifier.height(23.dp))
            Text(text = "Войдите, чтобы пользоваться функциями приложения")
            Spacer(modifier = Modifier.height(60.dp))
            Text(
                text = "Вход по E-mail",
                color = textColor,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            AppTextField(
                value = emailText,
                onValueChange = {
                    emailText = it

                    isEnabled = emailText.isNotEmpty()
                },
                contentPadding = PaddingValues(14.dp),
                placeholder = {
                    Text(
                        text = "example@mail.ru",
                        color = Color.Gray,
                        fontSize = 15.sp
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(32.dp))
            AppButton(
                text = "Далее",
                enabled = isEnabled,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (Regex("^[a-zA-Z0-9]*@[a-zA-Z0-9]*\\.[a-zA-Z]{2,}$").matches(emailText)) {
                    viewModel.sendEmailCode(emailText)

                    isLoading = true
                } else {
                    Toast.makeText(mContext, "Неправильный E-Mail!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 56.dp)
                .align(Alignment.BottomCenter)) {
                Text(
                    text = "Или войдите с помощью",
                    fontSize = 15.sp,
                    color = descriptionColor,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                AppButton(
                    text = "Войти с Яндекс",
                    fontWeight = FontWeight.W500,
                    color = Color.Black,
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                    borderStroke = BorderStroke(1.dp, inputColor),
                    modifier = Modifier.fillMaxWidth()
                ) {

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