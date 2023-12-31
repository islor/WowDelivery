package com.ianlor.wowdelivery.feature_delivery.data.repository

import androidx.paging.PagingSource
import androidx.paging.testing.asPagingSourceFactory
import com.ianlor.wowdelivery.feature_delivery.data.local.DeliveryDatabase
import com.ianlor.wowdelivery.feature_delivery.data.local.DeliveryEntity
import com.ianlor.wowdelivery.feature_delivery.data.local.FavouriteDelivery
import com.ianlor.wowdelivery.feature_delivery.data.local.RemoteKeys
import com.ianlor.wowdelivery.feature_delivery.data.mappers.toDelivery
import com.ianlor.wowdelivery.feature_delivery.data.mappers.toDeliveryEntity
import com.ianlor.wowdelivery.feature_delivery.data.remote.DeliveryApi
import com.ianlor.wowdelivery.feature_delivery.data.remote.DeliveryDto
import com.ianlor.wowdelivery.feature_delivery.data.remote.FakeDeliveryApi
import com.ianlor.wowdelivery.feature_delivery.domain.model.Delivery
import com.ianlor.wowdelivery.feature_delivery.domain.repository.DeliveriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flowOf

class FakeDeliveryRepository(val api: FakeDeliveryApi) :
    DeliveriesRepository {
    private val deliveriesEntity = mutableListOf<DeliveryEntity>()
    private val remoteKeys = mutableListOf<RemoteKeys>()
    private val favourites = mutableListOf<FavouriteDelivery>()

    private val pagingSourceFactory =
        deliveriesEntity.asPagingSourceFactory()

    val pagingSource = pagingSourceFactory()

    private lateinit var database: DeliveryDatabase
    suspend fun generateEntities() {
        api.generateDeliveries(20)
        deliveriesEntity.addAll(
            api.getDeliveries(0).map { it.toDeliveryEntity() })
    }

    override fun pagingSource(): PagingSource<Int, DeliveryEntity> {
        return pagingSource
    }

    override fun database(): DeliveryDatabase {
        return database
    }

    override suspend fun getDeliveriesFromApi(offset: Int): List<DeliveryDto> {
        return api.getDeliveries(offset)
    }

    override suspend fun clearAllDeliveries() {
        deliveriesEntity.clear()
    }

    override suspend fun upsertAllDeliveries(delivery: List<DeliveryEntity>) {
        deliveriesEntity.addAll(delivery)
    }

    override suspend fun insertAllRemoteKeys(remoteKey: List<RemoteKeys>) {
        remoteKeys.addAll(remoteKey)
    }

    override suspend fun getRemoteKeysRepoId(deliveryItemId: Int): RemoteKeys? {
        return remoteKeys.find { it.deliveryItemId == deliveryItemId }
    }

    override suspend fun clearAllRemoteKeys() {
        remoteKeys.clear()
    }

    override suspend fun getDeliveryById(id: Int): Delivery? {
        return deliveriesEntity[id].toDelivery()
    }

    override suspend fun isDeliveryFavourited(deliveryItemId: String): Boolean {
        return favourites.contains(FavouriteDelivery(deliveryItemId))
    }

    override suspend fun addFavouriteDelivery(favouriteDelivery: FavouriteDelivery) {
        favourites.add(favouriteDelivery)
    }

    override suspend fun clearAllFavourites() {
        favourites.clear()
    }

    override suspend fun removeFavourite(favouriteDelivery: FavouriteDelivery) {
        favourites.remove(favouriteDelivery)
    }

    override fun getAllFavourites(): Flow<List<FavouriteDelivery>> {
        return flowOf(favourites)
    }
}