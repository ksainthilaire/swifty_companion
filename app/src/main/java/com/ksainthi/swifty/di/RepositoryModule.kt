package com.ksainthi.swifty.di

import android.content.Context
import android.content.res.Resources
import android.renderscript.Matrix3f
import android.util.Log
import com.ksainthi.swifty.MainApplication
import com.ksainthi.swifty.data.api.Api42
import com.ksainthi.swifty.data.api.ApiService
import com.ksainthi.swifty.data.mapper.ApiMapper
import com.ksainthi.swifty.data.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideApplicationContext(@ApplicationContext context: Context): MainApplication =
        context as MainApplication

    @Provides
    @Singleton
    fun provideApi42(api: ApiService): Api42 = api.createApi42()

    @Singleton
    @Provides
    fun provideApiMapper(): ApiMapper = ApiMapper()

    @Singleton
    @Provides
    fun provideResources(application: MainApplication): Resources = application.resources


            @Provides
            @Singleton

    fun provideRepository(
        api: Api42,
        mapper: ApiMapper,
        resources: Resources
    ): Repository {
        return Repository(api, mapper, resources)
    }
}