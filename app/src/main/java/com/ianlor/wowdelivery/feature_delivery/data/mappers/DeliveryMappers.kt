package com.ianlor.wowdelivery.feature_delivery.data.mappers

import com.ianlor.wowdelivery.feature_delivery.data.local.DeliveryEntity
import com.ianlor.wowdelivery.feature_delivery.data.remote.DeliveryDto
import com.ianlor.wowdelivery.feature_delivery.domain.model.Delivery

fun DeliveryDto.toDeliveryEntity(): DeliveryEntity {
    val routeEntity = DeliveryEntity.Route(
        route?.start?:"",
        route?.end?:""
    )
    val senderEntity = DeliveryEntity.Sender(
        sender?.phone ?: "",
        sender?.name ?: "",
        sender?.email ?: "",
    )
    return DeliveryEntity(
        remoteId = id,
        goodsPicture = goodsPicture?:"",
        remarks = remarks?:"",
        pickupTime = pickupTime?:"",
        deliveryFee = deliveryFee?:"",
        surcharge = surcharge?:"",
        route = routeEntity,
        sender = senderEntity,
    )
}

fun DeliveryEntity.toDelivery(): Delivery {
    val routeDelivery = Delivery.Route(
        route.start,
        route.end
    )
    val senderDelivery = Delivery.Sender(
        sender.phone,
        sender.name,
        sender.email,
    )
    return Delivery(
        id = id,
        remoteId = remoteId,
        goodsPicture = goodsPicture,
        remarks = remarks,
        pickupTime = pickupTime,
        deliveryFee = deliveryFee,
        surcharge = surcharge,
        route = routeDelivery,
        sender = senderDelivery,
    )
}