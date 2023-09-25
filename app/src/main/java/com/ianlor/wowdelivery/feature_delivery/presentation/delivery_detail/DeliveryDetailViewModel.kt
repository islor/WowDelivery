package com.ianlor.wowdelivery.feature_delivery.presentation.delivery_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ianlor.wowdelivery.feature_delivery.data.local.FavouriteDelivery
import com.ianlor.wowdelivery.feature_delivery.domain.model.Delivery
import com.ianlor.wowdelivery.feature_delivery.domain.repository.DeliveriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DeliveryDetailViewModel @Inject constructor(
    private val repo: DeliveriesRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    var _delivery = mutableStateOf<Delivery?>(null)
    var delivery: State<Delivery?> = _delivery

    private val _isFavourite = mutableStateOf(false)
    val isFavourite: State<Boolean> = _isFavourite

    init {

        val id = savedStateHandle.get<Int>("id")!!
        if (id != -1) {

            viewModelScope.launch {
                _delivery.value = repo.getDeliveryById(id)
                _isFavourite.value =
                    repo.isDeliveryFavourited(delivery.value!!.remoteId)
            }
        }
    }

    fun onEvent(deliveryDetailEvents: DeliveryDetailEvents){
        when(deliveryDetailEvents){

            is DeliveryDetailEvents.OnAddFavourite -> {
                viewModelScope.launch {
                    repo.addFavouriteDelivery(FavouriteDelivery( deliveryDetailEvents.id))

                }
                _isFavourite.value = !isFavourite.value
            }
            is DeliveryDetailEvents.OnRemoveFavourite -> {
                viewModelScope.launch {
                    repo.removeFavourite(FavouriteDelivery( deliveryDetailEvents.id))
                }
                _isFavourite.value = !isFavourite.value
            }
        }
    }
}