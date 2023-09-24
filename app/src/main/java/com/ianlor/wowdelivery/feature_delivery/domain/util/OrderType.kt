package com.ianlor.wowdelivery.feature_delivery.domain.util

sealed class OrderType{
    object Ascending: OrderType()
    object Descending: OrderType()
}
