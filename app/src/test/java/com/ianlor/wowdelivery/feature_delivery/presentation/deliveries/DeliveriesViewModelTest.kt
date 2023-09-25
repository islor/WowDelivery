package com.ianlor.wowdelivery.feature_delivery.presentation.deliveries

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.testing.asSnapshot
import com.ianlor.wowdelivery.feature_delivery.data.remote.FakeDeliveryApi
import com.ianlor.wowdelivery.feature_delivery.data.repository.FakeDeliveryRepository
import com.ianlor.wowdelivery.feature_delivery.domain.model.Delivery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class DeliveriesViewModelTest{
    private lateinit var viewModel: DeliveriesViewModel
    private lateinit var api: FakeDeliveryApi
    private lateinit var deliveryRepository:FakeDeliveryRepository

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    class MainDispatcherRule(
        val testDispatcher: TestDispatcher = UnconfinedTestDispatcher(),
    ) : TestWatcher() {
        override fun starting(description: Description) {
            Dispatchers.setMain(testDispatcher)
        }

        override fun finished(description: Description) {
            Dispatchers.resetMain()
        }
    }
    @Before
    fun setup(){
        api = FakeDeliveryApi()
        deliveryRepository = FakeDeliveryRepository(api)

        val pager = Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                deliveryRepository.pagingSource()
            }
        )
        viewModel = DeliveriesViewModel(pager, deliveryRepository)
    }


    @Test
    fun `test pager is receiving Delivery items`() = runTest {
        deliveryRepository.generateEntities()

        val items = viewModel.deliveryPagingFlow.asSnapshot()
        assertTrue(items is List<Delivery>)
        assertTrue(items.size == 20)
    }
}