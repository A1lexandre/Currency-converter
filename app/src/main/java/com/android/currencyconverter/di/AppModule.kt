package com.android.currencyconverter.di

import com.android.currencyconverter.data.api.ApiService
import com.android.currencyconverter.data.api.InterceptorProvider
import com.android.currencyconverter.main.DefaultMainRepository
import com.android.currencyconverter.main.MainRepository
import com.android.currencyconverter.util.Constants.Api.BASE_URL
import com.android.currencyconverter.util.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApiConversion(): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(InterceptorProvider.getInterceptor())
            .build().create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesCoroutineDispatcher(): DispatcherProvider {
        return object: DispatcherProvider {
            override val io: CoroutineDispatcher
                get() = Dispatchers.IO
            override val main: CoroutineDispatcher
                get() = Dispatchers.Main
            override val default: CoroutineDispatcher
                get() = Dispatchers.Default
            override val unconfined: CoroutineDispatcher
                get() = Dispatchers.Unconfined
        }
    }

    @Provides
    @Singleton
    fun provideMainRepository(api: ApiService): MainRepository = DefaultMainRepository(api)

}