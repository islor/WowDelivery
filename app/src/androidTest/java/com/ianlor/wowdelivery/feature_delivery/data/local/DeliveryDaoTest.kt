package com.ianlor.wowdelivery.feature_delivery.data.local

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.testing.TestPager
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.ianlor.wowdelivery.feature_delivery.data.remote.FakeDeliveryApi
import com.ianlor.wowdelivery.feature_delivery.data.mappers.toDeliveryEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class DeliveryDaoTest {

    private lateinit var database: DeliveryDatabase
    private lateinit var dao: DeliveryDao
    private lateinit var api: FakeDeliveryApi

    @Before
    fun setup() {
        database =
            Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                DeliveryDatabase::class.java
            ).allowMainThreadQueries().build()
        dao = database.deliveryDao
        api = FakeDeliveryApi()
        api.generateDeliveries(10)

    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun pagingSource() = runTest {
        val items = api.getDeliveries(0).map {
            it.toDeliveryEntity()
        }
        dao.upsertAll(items)

        val pager = TestPager(PagingConfig(10), dao.pagingSource())

        val result = pager.refresh() as PagingSource.LoadResult.Page

        assertThat(result.data).containsExactlyElementsIn(items)
    }

    @Test
    fun upsertDeliveryItems() = runTest {
        val items = api.getDeliveries(0).map {
            it.toDeliveryEntity()
        }

        dao.upsertAll(items)

        val allItems = dao.getDeliveries().first()

        assertThat(allItems!!.size).isEqualTo(10)
        assertThat(allItems[0]).isEqualTo(items[0])
    }

    @Test
    fun getDeliveryItemsById() = runTest {
        val items = api.getDeliveries(0).map {
            it.toDeliveryEntity()
        }

        dao.upsertAll(items)

        val item = dao.getDelivery(1)

        assertThat(item).isNotNull()
        assertThat(item!!.id).isEqualTo(1)
    }

    @Test
    fun deleteDeliveryItems() = runTest {
        val items = api.getDeliveries(0).map {
            it.toDeliveryEntity()
        }

        dao.upsertAll(items)
        dao.clearAll()

        val allItems = dao.getDeliveries().first()

        assertThat(allItems!!.size).isEqualTo(0)
    }
}