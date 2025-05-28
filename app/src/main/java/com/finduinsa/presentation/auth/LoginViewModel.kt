package com.finduinsa.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.finduinsa.data.remote.api.AuthService
import com.finduinsa.data.remote.model.LoginRequest
import com.finduinsa.data.remote.model.ErrorResponse
import com.google.gson.Gson // Import Gson
import kotlinx.coroutines.flow.SharingStarted

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authService: AuthService // Inject AuthService
) : ViewModel() {

    private val _nim = MutableStateFlow("")
    val nim: StateFlow<String> = _nim.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    private val _loginError = MutableStateFlow<String?>(null)
    val loginError: StateFlow<String?> = _loginError.asStateFlow()

    val isLoginButtonEnabled: StateFlow<Boolean> = combine(
        nim,
        password
    ) { currentNim, currentPassword ->
        currentNim.isNotEmpty() && currentPassword.isNotEmpty()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )

    fun onNimChange(newNim: String) {
        _nim.value = newNim
        _loginError.value = null
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
        _loginError.value = null
    }

    fun login() {
        _loginError.value = null // Bersihkan error sebelumnya
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val request = LoginRequest(nim = _nim.value, password = _password.value)
                val response = authService.loginUser(request)

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        // Login berhasil
                        val loginResponse = response.body()
                        // TODO: Simpan token atau informasi pengguna ke SharedPreferences/DataStore
                        // Log.d("LoginViewModel", "Login berhasil: ${loginResponse?.token}")
                        _isLoggedIn.value = true
                    } else {
                        // Login gagal, ambil pesan error dari body response
                        val errorBody = response.errorBody()?.string()
                        val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                        _loginError.value = errorResponse?.detail ?: errorResponse?.nonFieldErrors?.firstOrNull() ?: "Login gagal. Periksa kembali NIM dan Kata Sandi Anda."
                        // Atau jika Django mengembalikan error validasi per field:
                        // errorResponse?.nim?.firstOrNull() ?: errorResponse?.password?.firstOrNull() ?: "..."
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _loginError.value = "Terjadi kesalahan jaringan: ${e.message}"
                }
            }
        }
    }

    fun onLoginSuccessHandled() {
        _isLoggedIn.value = false
        _loginError.value = null
    }

    fun onLoginErrorShown() {
        _loginError.value = null
    }
}