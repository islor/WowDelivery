package com.ianlor.wowdelivery.feature_delivery.data.mappers

import com.ianlor.wowdelivery.feature_delivery.data.local.DeliveryEntity
import com.ianlor.wowdelivery.feature_delivery.data.remote.DeliveryDto
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class DeliveryMappersKtTest{
    private lateinit var deliveryDto:DeliveryDto
    @Before
    fun setup(){
        deliveryDto = DeliveryDto(
            id = "1",
            goodsPicture = "Delivery A",
            remarks = "This is a remark This is a remark This is a remark This is a remark This is a remark This is a remark This is a remark",
            pickupTime = "2014-10-06T10:45:38-08:00",
            deliveryFee = "$92",
            surcharge = "$1221242",
            route = DeliveryDto.Route(
                start = "Central, Hong Lkong, Hong Kong",
                end = "TST"
            ),
            sender = DeliveryDto.Sender(
                phone = "2334144",
                name = "Ken",
                "kenisenough@mail.com"
            ),
        )
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