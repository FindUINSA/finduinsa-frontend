package com.finduinsa.data.remote.model

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("nama") val nama: String,
    @SerializedName("nim") val nim: String,
    @SerializedName("password") val password: String,
    @SerializedName("no_hp") val noHp: String?, // Mungkin opsional di Django
    @SerializedName("prodi") val prodi: String?, // Mungkin opsional
    @SerializedName("fakultas") val fakultas: String?, // Mungkin opsional
    @SerializedName("gambar") val gambar: String? // URL gambar KTM
)

data class LoginRequest(
    @SerializedName("nim") val nim: String,
    @SerializedName("password") val password: String
)