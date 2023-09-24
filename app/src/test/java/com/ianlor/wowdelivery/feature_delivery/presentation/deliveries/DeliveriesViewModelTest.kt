package com.ianlor.wowdelivery.feature_delivery.presentation.deliveries

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.ianlor.wowdelivery.feature_delivery.data.remote.DeliveryRemoteMediator
import com.ianlor.wowdelivery.feature_delivery.data.repository.FakeDeliveryRepository
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalPagingApi::class)
class DeliveriesViewModelTest{
    private lateinit var viewModel: DeliveriesViewModel

    @Before
    fun setup(){
        val deliveryRepository = FakeDeliveryRepository()
        val pager = Pager(
            config = PagingConfig(pageSize = 2),
            remoteMediator = DeliveryRemoteMediator(
                deliveryRepository
            ),
            pagingSourceFactory = {
                deliveryRepository.pagingSource()
            }
        )
        viewModel = DeliveriesViewModel(pager, deliveryRepository)
    }

    @Test
    fun `asd `(){

    }
}