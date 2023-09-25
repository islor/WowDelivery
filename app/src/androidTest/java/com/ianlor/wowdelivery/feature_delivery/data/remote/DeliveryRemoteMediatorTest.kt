package com.ianlor.wowdelivery.feature_delivery.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.ianlor.wowdelivery.feature_delivery.data.local.DeliveryDatabase
import com.ianlor.wowdelivery.feature_delivery.data.local.DeliveryEntity
import com.ianlor.wowdelivery.feature_delivery.data.local.repository.FakeDeliveryRepository
import com.ianlor.wowdelivery.feature_delivery.domain.repository.DeliveriesRepository
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalPagingApi::class)
class DeliveryRemoteMediatorTest {

    private lateinit var database: DeliveryDatabase
    private val fakeApi = FakeDeliveryApi()
    private lateinit var repo: DeliveriesRepository

    @Before
    fun setup() {
        database =
            Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                DeliveryDatabase::class.java
            ).allowMainThreadQueries().build()
        repo = FakeDeliveryRepository(database, fakeApi)
    }

    @After
    fun tearDown() {
        database.clearAllTables()
        database.close()
        fakeApi.failureMsg = null
    }

    @Test
    fun loadResultSuccessWithData() = runTest {
        fakeApi.generateDeliveries(10)
        val remoteMediator = DeliveryRemoteMediator(repo)
        val pagingState = PagingState<Int, DeliveryEntity>(
            listOf(),
            null,
            PagingConfig(10),
            10
        )
        val result =
            remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @Test
    fun loadSuccessEndOfPagination() = runTest {
        val remoteMediator = DeliveryRemoteMediator(repo)
        val pagingState = PagingState<Int, DeliveryEntity>(
            listOf(),
            null,
            PagingConfig(10),
            10
        )
        val result =
            remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertTrue((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @Test
    fun loadError() = runTest {
        fakeApi.failureMsg = "error"
        val remoteMediator = DeliveryRemoteMediator(repo)
        val pagingState = PagingState<Int, DeliveryEntity>(
            listOf(),
            null,
            PagingConfig(10),
            10
        )
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue (result is RemoteMediator.MediatorResult.Error )
    }
}