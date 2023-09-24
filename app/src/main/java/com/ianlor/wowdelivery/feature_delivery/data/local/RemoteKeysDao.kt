package com.ianlor.wowdelivery.feature_delivery.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKeys>)

    @Query("SELECT * FROM remoteKeys WHERE deliveryItemId = :deliveryItemId")
    suspend fun remoteKeysRepoId(deliveryItemId: Int): RemoteKeys?

    @Query("DELETE FROM remoteKeys")
    suspend fun clearRemoteKeys()
}