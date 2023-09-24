package com.ianlor.wowdelivery.feature_delivery.domain.util

sealed class ListOrder(val orderType: OrderType){
    class Title(orderType: OrderType): ListOrder(orderType)
    class Date(orderType: OrderType): ListOrder(orderType)
    class Fee(orderType: OrderType): ListOrder(orderType)

    fun copy(orderType: OrderType): ListOrder {
        return when(this){
            is Title -> Title(orderType)
            is Date -> Date(orderType)
            is Fee -> Fee(orderType)
        }
    }
}
