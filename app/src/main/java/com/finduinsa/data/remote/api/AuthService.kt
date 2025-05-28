package com.finduinsa.data.remote.api

import com.finduinsa.data.remote.model.LoginRequest
import com.finduinsa.data.remote.model.LoginResponse
import com.finduinsa.data.remote.model.RegisterRequest
import com.finduinsa.data.remote.model.RegisterResponse
import retrofit2.Response // Import Response dari Retrofit2
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("api/register/") // Sesuaikan dengan URL endpoint register Django Anda
    suspend fun registerUser(@Body request: RegisterRequest): Response<RegisterResponse>

    @POST("api/login/") // Sesuaikan dengan URL endpoint login Django Anda
    suspend fun loginUser(@Body request: LoginRequest): Response<LoginResponse>
}