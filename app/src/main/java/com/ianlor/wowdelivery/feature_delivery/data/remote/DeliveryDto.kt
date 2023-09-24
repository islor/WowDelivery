package com.ianlor.wowdelivery.feature_delivery.data.remote

data class DeliveryDto(val id: String,
                       val goodsPicture: String?,
                       val remarks: String?,
                       val pickupTime: String?,
                       val deliveryFee: String?,
                       val surcharge: String?,
                       val route: Route?,
                       val sender: Sender?,){

    data class Route(
        val start: String,
        val end: String
    )

    data class Sender(
        val phone: String,
        val name: String,
        val email: String,
    )
}
