package com.ianlor.wowdelivery.feature_delivery.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouritesDao {

    @Query("SELECT * FROM favouriteDelivery")
    fun getAllFavouriteDeliveries(): Flow<List<FavouriteDelivery>>

    @Query("SELECT * FROM favouriteDelivery WHERE deliveryItemId = :deliveryItemId")
    suspend fun getFavouriteById(deliveryItemId: String): FavouriteDelivery?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouriteDelivery(favouriteDelivery: FavouriteDelivery)

    @Query("DELETE FROM favouriteDelivery")
    suspend fun clearAll()

    @Delete
    suspend fun deleteFavourite(favouriteDelivery: FavouriteDelivery)
}