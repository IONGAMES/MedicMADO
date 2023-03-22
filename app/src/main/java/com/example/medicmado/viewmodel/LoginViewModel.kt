package com.example.medicmado.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicmado.utils.ApiService
import kotlinx.coroutines.launch

/*
Описание: Класс логики экранов авторизации
Дата создания: 22.03.2023 13:00
Автор: Георгий Хасанов
*/
class LoginViewModel: ViewModel() {
    val responseCode = MutableLiveData<Int>()
    val token = MutableLiveData<String>()
    val errorMessage = MutableLiveData<String>()

    /*
    Описание: Метод отправки кода на email
    Дата создания: 22.03.2023 13:00
    Автор: Георгий Хасанов
    */
    fun sendEmailCode(email: String) {
        responseCode.value = null
        token.value = null
        errorMessage.value = null

        viewModelScope.launch {
            try {
                val apiService = ApiService.getInstance()

                val json = apiService.sendCode(email)

                if (json.code() == 200) {
                    responseCode.value = 200
                } else {
                    errorMessage.value = json.code().toString()
                }
            } catch (e: Exception) {
                errorMessage.value = e.toString()
            }
        }
    }

    /*
    Описание: Проверка кода из email
    Дата создания: 22.03.2023 13:00
    Автор: Георгий Хасанов
    */
    fun checkEmailCode(email: String, code: String) {
        responseCode.value = null
        token.value = null
        errorMessage.value = null

        viewModelScope.launch {
            try {
                val apiService = ApiService.getInstance()

                val json = apiService.checkCode(email, code)

                if (json.code() == 200) {
                    token.value = json.body()!!.get("token").toString()
                } else {
                    errorMessage.value = json.code().toString()
                }
            } catch (e: Exception) {
                errorMessage.value = e.toString()
            }
        }
    }
}