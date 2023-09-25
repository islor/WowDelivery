package com.ianlor.wowdelivery.feature_delivery.data

import com.ianlor.wowdelivery.feature_delivery.data.remote.DeliveryDto
import java.util.concurrent.atomic.AtomicInteger

class DeliveryFactory {
    private val counter = AtomicInteger(0)

    fun createDeliveryDto() : DeliveryDto {
        val id = counter.incrementAndGet()
        val delivery = DeliveryDto(
            id = "name_$id",
            goodsPicture = "title $id",
            remarks = "remarks $id",
            pickupTime = "pickupTime $id",
            deliveryFee = " $$id",
            surcharge = "$$id",
            route = DeliveryDto.Route(
                start = "Central, Hong Lkong, Hong Kong",
                end = "TST"
            ),
            sender = DeliveryDto.Sender(
                phone = "2334144",
                name = "Ken",
                "kenisenough@mail.com"
            )
        )
        return delivery
    }
}