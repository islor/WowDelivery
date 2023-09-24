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
import com.ianlor.wowdelivery.feature_delivery.data.repository.DeliveriesRepositoryImpl
import com.ianlor.wowdelivery.feature_delivery.domain.repository.DeliveriesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton


@OptIn(ExperimentalPagingApi::class)
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDeliveryDatabase(app: Application): DeliveryDatabase {
        return Room.databaseBuilder(
            app,
            DeliveryDatabase::class.java,
            DeliveryDatabase.DB_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideDeliveryAPI(): DeliveryApi {
        return Retrofit.Builder().baseUrl(DeliveryApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }


    @Provides
    @Singleton
    fun provideDeliveryPager(
        deliveryRepository: DeliveriesRepository
    ): Pager<Int, DeliveryEntity> {

        return Pager(
            config = PagingConfig(pageSize = 2),
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
    fun provideDeliveryRepository(db: DeliveryDatabase, api: DeliveryApi): DeliveriesRepository {
        return DeliveriesRepositoryImpl(db, api)
    }
}
