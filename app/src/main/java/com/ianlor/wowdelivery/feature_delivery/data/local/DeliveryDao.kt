package com.ianlor.wowdelivery.feature_delivery.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface DeliveryDao {

    @Upsert
    suspend fun upsertAll(delivery: List<DeliveryEntity>)

    @Query("SELECT * FROM deliveryEntity")
    fun pagingSource(): PagingSource<Int, DeliveryEntity>

    @Query("DELETE FROM deliveryEntity")
    suspend fun clearAll()

    @Query("SELECT * FROM deliveryEntity")
    fun getDeliveries(): Flow<List<DeliveryEntity>>

    @Query("SELECT * FROM deliveryEntity WHERE id = :id")
    suspend fun getDelivery(id: Int): DeliveryEntity?
}