package com.ianlor.wowdelivery.feature_delivery.data.remote

import com.ianlor.wowdelivery.feature_delivery.data.DeliveryFactory
import java.io.IOException

class FakeDeliveryApi: DeliveryApi {
    var failureMsg: String? = null

    private val deliveries = mutableListOf<DeliveryDto>()
    private val deliveryFactory = DeliveryFactory()
    fun generateDeliveries(count:Int){
        repeat((1..count).count()) {
            deliveries.add(deliveryFactory.createDeliveryDto())
        }
    }
    override suspend fun getDeliveries(offset: Int): List<DeliveryDto> {
        failureMsg?.let {
            throw IOException(it)
        }
        return deliveries
    }
}