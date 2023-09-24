package com.ianlor.wowdelivery.feature_delivery.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface DeliveryApi {

    @GET("deliveries")
    suspend fun getDeliveries(@Query("offset") offset: Int): List<DeliveryDto>

    companion object{
        const val BASE_URL = "https://6285f87796bccbf32d6c0e6a.mockapi.io"
    }
}