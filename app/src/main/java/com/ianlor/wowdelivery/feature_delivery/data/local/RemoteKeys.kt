package com.ianlor.wowdelivery.feature_delivery.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RemoteKeys(
    val prevKey: Int?,
    val nextKey: Int?
) {

    @PrimaryKey(autoGenerate = true)
    var deliveryItemId: Int = 0
}
