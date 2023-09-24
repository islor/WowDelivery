package com.ianlor.wowdelivery.feature_delivery.data.repository

import androidx.paging.Pager
import androidx.paging.PagingSource
import com.ianlor.wowdelivery.feature_delivery.data.local.DeliveryDatabase
import com.ianlor.wowdelivery.feature_delivery.data.local.DeliveryEntity
import com.ianlor.wowdelivery.feature_delivery.data.local.FavouriteDelivery
import com.ianlor.wowdelivery.feature_delivery.data.local.RemoteKeys
import com.ianlor.wowdelivery.feature_delivery.data.mappers.toDelivery
import com.ianlor.wowdelivery.feature_delivery.data.remote.DeliveryDto
import com.ianlor.wowdelivery.feature_delivery.domain.model.Delivery
import com.ianlor.wowdelivery.feature_delivery.domain.repository.DeliveriesRepository
import kotlinx.coroutines.flow.Flow

class FakeDeliveryRepository:DeliveriesRepository {
    private val deliveriesEntity = mutableListOf<DeliveryEntity>()

    override fun pagingSource(): PagingSource<Int, DeliveryEntity> {
        TODO("Not yet implemented")
    }

    override fun database(): DeliveryDatabase {
        TODO("Not yet implemented")
    }

    override suspend fun getDeliveriesFromApi(offset: Int): List<DeliveryDto> {
        TODO("Not yet implemented")
    }

    override suspend fun clearAllDeliveries() {
        deliveriesEntity.clear()
    }

    override suspend fun upsertAllDeliveries(delivery: List<DeliveryEntity>) {
        deliveriesEntity.addAll(delivery)
    }

    override suspend fun insertAllRemoteKeys(remoteKey: List<RemoteKeys>) {
        TODO("Not yet implemented")
    }

    override suspend fun getRemoteKeysRepoId(deliveryItemId: Int): RemoteKeys? {
        TODO("Not yet implemented")
    }

    override suspend fun clearAllRemoteKeys() {
        TODO("Not yet implemented")
    }

    override suspend fun getDeliveryById(id: Int): Delivery? {
        return deliveriesEntity[id].toDelivery()
    }

    override suspend fun isDeliveryFavourited(deliveryItemId: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun addFavouriteDelivery(favouriteDelivery: FavouriteDelivery) {
        TODO("Not yet implemented")
    }

    override suspend fun clearAllFavourites() {
        TODO("Not yet implemented")
    }

    override suspend fun removeFavourite(favouriteDelivery: FavouriteDelivery) {
        TODO("Not yet implemented")
    }

    override fun getAllFavourites(): Flow<List<FavouriteDelivery>> {
        TODO("Not yet implemented")
    }
}