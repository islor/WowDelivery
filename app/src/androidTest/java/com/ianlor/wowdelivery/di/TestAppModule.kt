package com.ianlor.wowdelivery.di

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.ianlor.wowdelivery.feature_delivery.data.local.DeliveryDatabase
import com.ianlor.wowdelivery.feature_delivery.data.local.DeliveryEntity
import com.ianlor.wowdelivery.feature_delivery.data.remote.DeliveryApi
import com.ianlor.wowdelivery.feature_delivery.data.remote.DeliveryRemoteMediator
import com.ianlor.wowdelivery.feature_delivery.data.remote.FakeDeliveryApi
import com.ianlor.wowdelivery.feature_delivery.data.repository.DeliveriesRepositoryImpl
import com.ianlor.wowdelivery.feature_delivery.domain.repository.DeliveriesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@OptIn(ExperimentalPagingApi::class)
@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Singleton
    fun provideDeliveryDatabase(app: Application): DeliveryDatabase {
        return Room.inMemoryDatabaseBuilder(
            app,
            DeliveryDatabase::class.java
        ).build()
    }


    @Provides
    @Singleton
    fun provideDeliveryAPI(): DeliveryApi {
        val api = FakeDeliveryApi()
        api.generateDeliveries(20)
        return api
    }


    @Provides
    @Singleton
    fun provideDeliveryPager(
        deliveryRepository: DeliveriesRepository
    ): Pager<Int, DeliveryEntity> {

        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = DeliveryRemoteMediator(
                deliveryRepository
            ),
            pagingSourceFactory = {
                deliveryRepository.pagingSource()
            }
        )
    }

    @Provides
    @Singleton
    fun provideDeliveryRepository(
        db: DeliveryDatabase,
        api: DeliveryApi
    ): DeliveriesRepository {
        return DeliveriesRepositoryImpl(db, api)
    }
}