package com.finduinsa.data.remote.model


import com.google.gson.annotations.SerializedName

// Contoh response untuk login berhasil (misal: mengembalikan token dan info user)
data class LoginResponse(
    @SerializedName("token") val token: String,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("nim") val nim: String,
    @SerializedName("nama") val nama: String,
    @SerializedName("role") val role: String
    // Tambahkan field lain yang mungkin dikembalikan server setelah login
)

// Contoh response untuk register berhasil (seringkali hanya pesan sukses atau data user yang dibuat)
data class RegisterResponse(
    @SerializedName("message") val message: String? = null,
    @SerializedName("nim") val nim: String? = null,
    @SerializedName("id") val id: Int? = null
    // Tambahkan field lain yang mungkin dikembalikan server setelah register
)

// Struktur untuk error umum dari API
data class ErrorResponse(
    @SerializedName("detail") val detail: String? = null,
    @SerializedName("non_field_errors") val nonFieldErrors: List<String>? = null,
    // Tambahkan field error spesifik dari validasi Django jika ada (misal: "nim": ["nim ini sudah ada"])
    @SerializedName("nim") val nim: List<String>? = null,
    @SerializedName("password") val password: List<String>? = null
)