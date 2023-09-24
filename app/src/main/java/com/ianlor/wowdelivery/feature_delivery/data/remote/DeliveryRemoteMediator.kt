package com.ianlor.wowdelivery.feature_delivery.data.remote

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.ianlor.wowdelivery.feature_delivery.data.local.DeliveryEntity
import com.ianlor.wowdelivery.feature_delivery.data.local.RemoteKeys
import com.ianlor.wowdelivery.feature_delivery.data.mappers.toDeliveryEntity
import com.ianlor.wowdelivery.feature_delivery.domain.repository.DeliveriesRepository
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class DeliveryRemoteMediator(
    private val repo: DeliveriesRepository
) : RemoteMediator<Int, DeliveryEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, DeliveryEntity>
    ): MediatorResult {

        return try {

            val page  = when (loadType) {
                LoadType.REFRESH -> {

                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextKey?.minus(1) ?: Companion.STARTING_PAGE_INDEX
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    // If remoteKeys is null, that means the refresh result is not in the database yet.
                    val prevKey = remoteKeys?.prevKey
                    if (prevKey == null) {
                        return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    }
                    prevKey
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextKey = remoteKeys?.nextKey

                    if (nextKey == null) {
                        return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    }
                    nextKey
                }
            }

            val deliveries = repo.getDeliveriesFromApi(page)
            Log.d("MEDIATOR", "${loadType.name} isEmpty? ${deliveries.isEmpty()} $page" )
            val endOfPaginationReached = deliveries.isEmpty()
            repo.database().withTransaction {
                if (loadType == LoadType.REFRESH){
                    repo.clearAllDeliveries()
                    repo.clearAllRemoteKeys()
                }
                val prevKey = if (page == Companion.STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val deliveryEntities = deliveries.map { it.toDeliveryEntity() }
                val keys = deliveryEntities.map {
                    RemoteKeys(prevKey = prevKey, nextKey = nextKey)
                }
                repo.insertAllRemoteKeys(keys)
                repo.upsertAllDeliveries(deliveryEntities)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)

        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }

    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, DeliveryEntity>): RemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { entity ->
                // Get the remote keys of the last item retrieved
                repo.getRemoteKeysRepoId(entity.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, DeliveryEntity>): RemoteKeys? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { entity ->
                // Get the remote keys of the first items retrieved
                repo.getRemoteKeysRepoId(entity.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, DeliveryEntity>
    ): RemoteKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                repo.getRemoteKeysRepoId(repoId)
            }
        }
    }

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }

}