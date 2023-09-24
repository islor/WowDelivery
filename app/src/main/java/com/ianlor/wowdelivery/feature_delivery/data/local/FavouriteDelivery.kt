package com.ianlor.wowdelivery.feature_delivery.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavouriteDelivery(
    @PrimaryKey val deliveryItemId: String)