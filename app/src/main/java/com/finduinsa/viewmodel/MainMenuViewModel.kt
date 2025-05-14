package com.finduinsa.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainMenuViewModel : ViewModel() {

    private val _welcomeText = MutableStateFlow("Selamat Datang di FindUINSA")
    val welcomeText: StateFlow<String> = _welcomeText
}