package com.ianlor.wowdelivery.feature_delivery.data.mappers

import com.ianlor.wowdelivery.feature_delivery.data.DeliveryFactory
import com.ianlor.wowdelivery.feature_delivery.data.local.DeliveryEntity
import com.ianlor.wowdelivery.feature_delivery.data.remote.DeliveryDto
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class DeliveryMappersKtTest{
    private lateinit var deliveryDto:DeliveryDto
    @Before
    fun setup(){
        val deliveryFactory = DeliveryFactory()
        deliveryDto = deliveryFactory.createDeliveryDto()
    }

    @Test
    fun `Test DeliveryDTO converts to DeliveryEntity`(){
        val entity = deliveryDto.toDeliveryEntity()

        assertTrue(entity.remoteId.equals(deliveryDto.id))
        assertTrue(entity.goodsPicture.equals(deliveryDto.goodsPicture))
        assertTrue(entity.deliveryFee.equals(deliveryDto.deliveryFee))
        assertTrue(entity.surcharge.equals(deliveryDto.surcharge))
        assertTrue(entity.pickupTime.equals(deliveryDto.pickupTime))
    }

    @Test
    fun `Test DeliveryEntity converts to Delivery`(){
        val entity = deliveryDto.toDeliveryEntity()
        val delivery = entity.toDelivery()

        assertTrue(delivery.remoteId.equals(deliveryDto.id))
        assertTrue(delivery.goodsPicture.equals(deliveryDto.goodsPicture))
        assertTrue(delivery.deliveryFee.equals(deliveryDto.deliveryFee))
        assertTrue(delivery.surcharge.equals(deliveryDto.surcharge))
        assertTrue(delivery.pickupTime.equals(deliveryDto.pickupTime))
    }
}