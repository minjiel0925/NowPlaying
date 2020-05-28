package org.example.mjworkspace.myapplication.di

import android.app.Application
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import org.example.mjworkspace.myapplication.api.END_POINT
import org.example.mjworkspace.myapplication.api.MovieService
import org.example.mjworkspace.myapplication.fragments.m.data.now.NowRemoteDataSource
import org.example.mjworkspace.myapplication.fragments.m.room.MyDatabase
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class, CoreDataModule::class])
class AppModule {

    // service
    @Provides
    @Singleton
    fun provideMovieService(@MovieApi okHttpClient: OkHttpClient, moshiConverterFactory: MoshiConverterFactory) =
        provideService(okHttpClient, moshiConverterFactory, MovieService::class.java)

    // data source
    @Provides
    @Singleton
    fun provideNowRemoteDataSource(movieService: MovieService) =
        NowRemoteDataSource(
            movieService
        )

    @MovieApi
    @Provides
    fun providePrivateOkHttpClient(
        upstreamClient: OkHttpClient
    ): OkHttpClient {
        return upstreamClient.newBuilder()
            .build()
    }

    // database
    @Provides
    @Singleton
    fun provideDb(app: Application) = MyDatabase.getInstance(app)

    // dao
    @Provides
    @Singleton
    fun provideNowDao(mydb: MyDatabase) = mydb.nowDao()

    // coroutines
    @CoroutineScopeIO
    @Provides
    fun provideCoroutineScopeIO() = CoroutineScope(Dispatchers.IO)


    private fun createRetrofit(
        okhttpClient: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(END_POINT)
            .client(okhttpClient)
            .addConverterFactory(moshiConverterFactory)
            .build()
    }

    private fun <T> provideService(
        okhttpClient: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory,
        clazz: Class<T>
    ): T {
        return createRetrofit(okhttpClient, moshiConverterFactory).create(clazz)
    }
}