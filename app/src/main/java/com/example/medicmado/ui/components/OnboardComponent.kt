package com.example.medicmado.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.medicmado.ui.theme.descriptionColor
import com.example.medicmado.ui.theme.onboardTitleColor

/*
Описание: Компонент приветсвенного экрана
Дата создания: 22.03.2023 10:15
Автор: Георгий Хасанов
*/
@Composable
fun OnboardComponent(
    title: String,
    description: String
) {
    Column(modifier = Modifier
        .widthIn(max = 210.dp)
        .fillMaxWidth()
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.W600,
            color = onboardTitleColor,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = description,
            color = descriptionColor,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}