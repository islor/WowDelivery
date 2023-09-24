package com.ianlor.wowdelivery.feature_delivery.data.local

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DeliveryEntity(
    val remoteId: String,
    val goodsPicture: String,
    val remarks: String,
    val pickupTime: String,
    val deliveryFee: String,
    val surcharge: String,
    @Embedded val route: Route,
    @Embedded val sender: Sender,
    ) {

    @PrimaryKey(autoGenerate = true) var id: Int = 0

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
