package com.ianlor.wowdelivery.feature_delivery.domain.repository

import androidx.paging.PagingSource
import com.ianlor.wowdelivery.feature_delivery.data.local.DeliveryDatabase
import com.ianlor.wowdelivery.feature_delivery.data.local.DeliveryEntity
import com.ianlor.wowdelivery.feature_delivery.data.local.FavouriteDelivery
import com.ianlor.wowdelivery.feature_delivery.data.local.RemoteKeys
import com.ianlor.wowdelivery.feature_delivery.data.remote.DeliveryDto
import com.ianlor.wowdelivery.feature_delivery.domain.model.Delivery
import kotlinx.coroutines.flow.Flow

interface DeliveriesRepository {

    fun pagingSource(): PagingSource<Int, DeliveryEntity>

    fun database(): DeliveryDatabase

    suspend fun getDeliveriesFromApi(offset: Int): List<DeliveryDto>

    suspend fun clearAllDeliveries()

    suspend fun upsertAllDeliveries(delivery: List<DeliveryEntity>)

    suspend fun insertAllRemoteKeys(remoteKey: List<RemoteKeys>)

    suspend fun getRemoteKeysRepoId(deliveryItemId: Int): RemoteKeys?

    suspend fun clearAllRemoteKeys()

    suspend fun getDeliveryById(id: Int): Delivery?

    suspend fun isDeliveryFavourited(deliveryItemId: String): Boolean

    suspend fun addFavouriteDelivery(favouriteDelivery: FavouriteDelivery)

    suspend fun clearAllFavourites()

    suspend fun removeFavourite(favouriteDelivery: FavouriteDelivery)

    fun getAllFavourites(): Flow<List<FavouriteDelivery>>
}