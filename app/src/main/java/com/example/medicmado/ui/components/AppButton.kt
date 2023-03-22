package com.example.medicmado.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medicmado.R
import com.example.medicmado.ui.theme.*

/*
Описание: Кнопка приложения
Дата создания: 22.03.2023 12:10
Автор: Георгий Хасанов
*/
@Composable
fun AppButton(
    modifier: Modifier = Modifier,
    text: String,
    colors: ButtonColors = ButtonDefaults.buttonColors(backgroundColor = primaryColor, disabledBackgroundColor = primaryDisabledColor),
    fontSize: TextUnit = 17.sp,
    fontWeight: FontWeight = FontWeight.W600,
    color: Color = Color.White,
    contentPadding: PaddingValues = PaddingValues(16.dp),
    borderStroke: BorderStroke = BorderStroke(0.dp, strokeColor),
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        contentPadding = contentPadding,
        colors = colors,
        border = borderStroke,
        enabled = enabled,
        elevation = ButtonDefaults.elevation(0.dp),
        onClick = onClick
    ) {
        Text(
            text = text,
            fontSize = fontSize,
            fontWeight = fontWeight,
            color = color
        )
    }
}

/*
Описание: Кнопка приложения
Дата создания: 22.03.2023 12:10
Автор: Георгий Хасанов
*/
@Composable
fun AppBackButton(
    modifier: Modifier = Modifier,
    borderStroke: BorderStroke = BorderStroke(0.dp, strokeColor),
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.clickable {
            onClick()
        },
        backgroundColor = inputColor,
        shape = RoundedCornerShape(8.dp),
        border = borderStroke,
        elevation = 0.dp,
    ) {
        Icon(painter = painterResource(
            id = R.drawable.ic_back),
            contentDescription = "",
            tint = textColor,
            modifier = Modifier.padding(6.dp)
        )
    }
}