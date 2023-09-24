package com.ianlor.wowdelivery.feature_delivery.data.repository

import androidx.paging.PagingSource
import com.ianlor.wowdelivery.feature_delivery.data.local.DeliveryDatabase
import com.ianlor.wowdelivery.feature_delivery.data.local.DeliveryEntity
import com.ianlor.wowdelivery.feature_delivery.data.local.FavouriteDelivery
import com.ianlor.wowdelivery.feature_delivery.data.local.RemoteKeys
import com.ianlor.wowdelivery.feature_delivery.data.mappers.toDelivery
import com.ianlor.wowdelivery.feature_delivery.data.remote.DeliveryApi
import com.ianlor.wowdelivery.feature_delivery.data.remote.DeliveryDto
import com.ianlor.wowdelivery.feature_delivery.domain.model.Delivery
import com.ianlor.wowdelivery.feature_delivery.domain.repository.DeliveriesRepository
import kotlinx.coroutines.flow.Flow

class DeliveriesRepositoryImpl(private val db: DeliveryDatabase, private val api: DeliveryApi): DeliveriesRepository {
    override fun pagingSource(): PagingSource<Int, DeliveryEntity> {
        return db.deliveryDao.pagingSource()
    }

    override fun database(): DeliveryDatabase {
        return db
    }

    override suspend fun getDeliveriesFromApi(offset: Int): List<DeliveryDto> {
        return api.getDeliveries(offset)
    }

    override suspend fun clearAllDeliveries() {
        db.deliveryDao.clearAll()
    }

    override suspend fun upsertAllDeliveries(deliveries: List<DeliveryEntity>) {
        db.deliveryDao.upsertAll(deliveries)
    }

    override suspend fun insertAllRemoteKeys(remoteKeys: List<RemoteKeys>) {
        db.remoteKeysDao.insertAll(remoteKeys)
    }

    override suspend fun getRemoteKeysRepoId(deliveryItemId: Int): RemoteKeys? {
        return db.remoteKeysDao.remoteKeysRepoId(deliveryItemId)
    }

    override suspend fun clearAllRemoteKeys() {
        db.remoteKeysDao.clearRemoteKeys()
    }

    override suspend fun getDeliveryById(id: Int): Delivery? {
        return db.deliveryDao.getDelivery(id)?.toDelivery()
    }

    override suspend fun isDeliveryFavourited(deliveryItemId: String): Boolean {
        return db.favouritesDao.getFavouriteById(deliveryItemId) != null
    }

    override suspend fun addFavouriteDelivery(favouriteDelivery: FavouriteDelivery) {
        db.favouritesDao.insertFavouriteDelivery(favouriteDelivery)
    }

    override suspend fun clearAllFavourites() {
        db.favouritesDao.clearAll()
    }

    override suspend fun removeFavourite(favouriteDelivery: FavouriteDelivery) {
        db.favouritesDao.deleteFavourite(favouriteDelivery)
    }

    override fun getAllFavourites(): Flow<List<FavouriteDelivery>> {
        return db.favouritesDao.getAllFavouriteDeliveries()
    }

}