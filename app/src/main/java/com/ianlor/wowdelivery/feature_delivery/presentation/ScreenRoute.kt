package com.ianlor.wowdelivery.feature_delivery.presentation

sealed class ScreenRoute(val route: String){
    object DeliveriesScreen: ScreenRoute("deliveries_screen")
    object DeliveryDetailsScreen: ScreenRoute("delivery_details_screen")
}
