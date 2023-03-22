package com.example.medicmado.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.medicmado.ui.components.OnboardComponent
import com.example.medicmado.ui.theme.MedicMADOTheme
import com.example.medicmado.ui.theme.primaryColor
import com.example.medicmado.ui.theme.secondaryColor
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.flow.collect
import com.example.medicmado.R

/*
Описание: Класс приветсвенного экрана
Дата создания: 22.03.2023 10:00
Автор: Георгий Хасанов
*/
class OnboardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MedicMADOTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    OnboardContent()
                }
            }
        }
    }

    /*
    Описание: Контент приветсвенного экрана
    Дата создания: 22.03.2023 10:00
    Автор: Георгий Хасанов
    */
    @OptIn(ExperimentalPagerApi::class)
    @Composable
    fun OnboardContent() {
        val mContext = LocalContext.current
        val sharedPreferences = this.getSharedPreferences("shared", Context.MODE_PRIVATE)

        var selectedImage by rememberSaveable { mutableStateOf(R.drawable.onboard_1) }

        val pagerState = rememberPagerState()

        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.currentPage }.collect {
                when (it) {
                    0 -> {
                        selectedImage = R.drawable.onboard_1
                    }
                    1 -> {
                        selectedImage = R.drawable.onboard_2
                    }
                    2 -> {
                        selectedImage = R.drawable.onboard_3
                    }
                }
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxSize()
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .padding(start = 30.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = if (pagerState.currentPage != 2) "Пропустить" else "Завершить",
                        color = secondaryColor,
                        modifier = Modifier.clickable {
                            with(sharedPreferences.edit()) {
                                putBoolean("isFirstEnter", false)
                                apply()
                            }

                            val intent = Intent(mContext, LoginActivity::class.java)
                            startActivity(intent)
                        }
                    )
                    Image(
                        painter = painterResource(id = com.example.medicmado.R.drawable.ic_shape_2),
                        contentDescription = ""
                    )
                }
                HorizontalPager(
                    count = 3,
                    state = pagerState
                ) {
                    when (it) {
                        0 -> {
                            OnboardComponent(
                                title = "Анализы",
                                description = "Экспресс сбор и получение проб"
                            )
                        }
                        1 -> {
                            OnboardComponent(
                                title = "Уведомления",
                                description = "Вы быстро узнаете о результатах"
                            )
                        }
                        2 -> {
                            OnboardComponent(
                                title = "Мониторинг",
                                description = "Наши врачи всегда наблюдают за вашими показателями здоровья"
                            )
                        }
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    for (i in 0..2) {
                        Box(modifier = Modifier
                            .padding(4.dp)
                            .size(13.dp)
                            .clip(CircleShape)
                            .background(if (pagerState.currentPage == i) secondaryColor else Color.White)
                            .border(1.dp, secondaryColor, CircleShape)
                        )
                    }
                }
                Image(
                    painter = painterResource(id = selectedImage),
                    contentDescription = "",
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier.fillMaxWidth().height(210.dp).padding(bottom = 70.dp)
                )
            }
        }
    }
}