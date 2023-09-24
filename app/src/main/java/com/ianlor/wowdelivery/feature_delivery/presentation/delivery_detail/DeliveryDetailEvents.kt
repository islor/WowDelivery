package com.ianlor.wowdelivery.feature_delivery.presentation.delivery_detail

sealed class DeliveryDetailEvents{
    data class OnAddFavourite(val id:String):DeliveryDetailEvents()
    data class OnRemoveFavourite(val id:String):DeliveryDetailEvents()
}
