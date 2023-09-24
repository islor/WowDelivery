package com.ianlor.wowdelivery.feature_delivery.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [DeliveryEntity::class, RemoteKeys::class, FavouriteDelivery::class],
    version = 1,
    exportSchema = false
)
abstract class DeliveryDatabase : RoomDatabase() {
    abstract val deliveryDao: DeliveryDao
    abstract val remoteKeysDao: RemoteKeysDao
    abstract val favouritesDao: FavouritesDao
    companion object{
        const val DB_NAME = "delivery_db"
    }
}