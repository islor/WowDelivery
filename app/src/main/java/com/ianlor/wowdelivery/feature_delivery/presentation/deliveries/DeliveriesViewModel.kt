package com.ianlor.wowdelivery.feature_delivery.presentation.deliveries

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.ianlor.wowdelivery.feature_delivery.data.local.DeliveryEntity
import com.ianlor.wowdelivery.feature_delivery.data.mappers.toDelivery
import com.ianlor.wowdelivery.feature_delivery.domain.repository.DeliveriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DeliveriesViewModel @Inject constructor(pager: Pager<Int, DeliveryEntity>,
                                              private val repo: DeliveriesRepository) :
    ViewModel() {

    val deliveryPagingFlow = pager.flow.map { pagingData ->
        pagingData.map { it.toDelivery() }
    }.cachedIn(viewModelScope)

    var favouriteListState = mutableStateListOf<String>()

    init {
        getFavourites()
    }

    private fun getFavourites() {
        repo.getAllFavourites()
            .onEach { fav ->
                favouriteListState.clear()
                fav.map {
                    favouriteListState.add(it.deliveryItemId)
                }
            }.launchIn(viewModelScope)
    }
}