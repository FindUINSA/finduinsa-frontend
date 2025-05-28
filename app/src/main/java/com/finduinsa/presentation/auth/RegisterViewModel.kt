package com.finduinsa.presentation.auth

import kotlinx.coroutines.flow.SharingStarted

import android.net.Uri
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
import com.finduinsa.data.remote.model.RegisterRequest
import com.finduinsa.data.remote.model.ErrorResponse
import com.google.gson.Gson // Import Gson

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authService: AuthService // Inject AuthService
) : ViewModel() {

    private val _nim = MutableStateFlow("")
    val nim: StateFlow<String> = _nim.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _fullName = MutableStateFlow("")
    val fullName: StateFlow<String> = _fullName.asStateFlow()

    private val _studyProgram = MutableStateFlow("Pilih Program Studi")
    val studyProgram: StateFlow<String> = _studyProgram.asStateFlow()

    private val _faculty = MutableStateFlow("Pilih Fakultas")
    val faculty: StateFlow<String> = _faculty.asStateFlow()

    private val _phoneNumber = MutableStateFlow("")
    val phoneNumber: StateFlow<String> = _phoneNumber.asStateFlow()

    private val _ktmUri = MutableStateFlow<Uri?>(null)
    val ktmUri: StateFlow<Uri?> = _ktmUri.asStateFlow()

    private val _isRegistered = MutableStateFlow(false)
    val isRegistered: StateFlow<Boolean> = _isRegistered.asStateFlow()

    private val _registerError = MutableStateFlow<String?>(null) // State untuk pesan error register
    val registerError: StateFlow<String?> = _registerError.asStateFlow()

    val isRegisterButtonEnabled: StateFlow<Boolean> = combine(
        nim,
        password,
        fullName,
        studyProgram,
        faculty,
        phoneNumber,
        ktmUri
    ) { values ->
        val currentNim = values[0] as String
        val currentPassword = values[1] as String
        val currentFullName = values[2] as String
        val currentStudyProgram = values[3] as String
        val currentFaculty = values[4] as String
        val currentPhoneNumber = values[5] as String
        val currentKtmUri = values[6] as Uri?

        currentNim.isNotEmpty() &&
                currentPassword.isNotEmpty() &&
                currentFullName.isNotEmpty() &&
                currentStudyProgram != "Pilih Program Studi" &&
                currentFaculty != "Pilih Fakultas" &&
                currentPhoneNumber.isNotEmpty() && currentPhoneNumber.removePrefix("+62").isNotBlank() &&
                currentKtmUri != null
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )

    fun onNimChange(newNim: String) {
        _nim.value = newNim
        _registerError.value = null // Reset error saat input berubah
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
        _registerError.value = null // Reset error saat input berubah
    }

    fun onFullNameChange(newFullName: String) {
        _fullName.value = newFullName
        _registerError.value = null // Reset error saat input berubah
    }

    fun onStudyProgramChange(newStudyProgram: String) {
        _studyProgram.value = newStudyProgram
        _registerError.value = null // Reset error saat input berubah
    }

    fun onFacultyChange(newFaculty: String) {
        _faculty.value = newFaculty
        _registerError.value = null // Reset error saat input berubah
    }

    fun onPhoneNumberChange(newPhoneNumber: String) {
        _phoneNumber.value = newPhoneNumber
        _registerError.value = null // Reset error saat input berubah
    }

    fun onKtmUriChange(uri: Uri?) {
        _ktmUri.value = uri
        _registerError.value = null // Reset error saat input berubah
    }

    fun register() {
        if (!isRegisterButtonEnabled.value) {
            // Ini seharusnya tidak terpanggil jika tombol disabled, tapi sebagai safeguard
            _registerError.value = "Mohon lengkapi semua kolom yang wajib diisi dan unggah KTM."
            return
        }

        _registerError.value = null // Bersihkan error sebelumnya
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Konversi Uri KTM menjadi String (misal: untuk URL upload atau base64)
                // Untuk tahap ini, kita anggap URI adalah representasi string yang valid untuk gambar
                val ktmImageUrl = _ktmUri.value?.toString()

                val request = RegisterRequest(
                    nama = _fullName.value,
                    nim = _nim.value,
                    password = _password.value,
                    noHp = _phoneNumber.value.removePrefix("+62"), // Kirim tanpa +62
                    prodi = _studyProgram.value,
                    fakultas = _faculty.value,
                    gambar = ktmImageUrl // Kirim URL gambar KTM
                )
                val response = authService.registerUser(request)

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        // Pendaftaran berhasil
                        _isRegistered.value = true
                    } else {
                        // Pendaftaran gagal, ambil pesan error dari body response
                        val errorBody = response.errorBody()?.string()
                        val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)

                        // Coba ambil pesan error spesifik atau pesan umum
                        _registerError.value = errorResponse?.nim?.firstOrNull() ?: // Jika ada error pada NIM
                                errorResponse?.password?.firstOrNull() ?: // Jika ada error pada password
                                errorResponse?.detail ?: // Detail error umum
                                errorResponse?.nonFieldErrors?.firstOrNull() ?: // Error non-field
                                "Pendaftaran gagal. Pastikan data unik atau valid."
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _registerError.value = "Terjadi kesalahan jaringan: ${e.message}"
                }
            }
        }
    }

    fun onRegisterSuccessHandled() {
        _isRegistered.value = false
        _registerError.value = null
    }

    fun onRegisterErrorShown() {
        _registerError.value = null
    }
}