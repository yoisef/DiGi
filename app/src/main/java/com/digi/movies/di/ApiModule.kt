package com.digi.movies.di


import android.app.Application
import androidx.room.Room
import com.digi.movies.data.db.MovieDao
import com.digi.movies.data.db.MovieDatabase
import com.digi.movies.data.repository.MainRepoImp
import com.digi.movies.domain.repository.MainRepo
import com.digi.movies.network.EndPoint
import com.digi.movies.network.QueryParameterAddInterceptor
import com.digi.movies.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor{
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }
    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(QueryParameterAddInterceptor())
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()


    }



    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): EndPoint {
        return retrofit.create(EndPoint::class.java)
    }

    @Provides
    fun provideRepo(apiService: EndPoint,dao: MovieDao): MainRepo{
        return MainRepoImp(apiService,dao)
    }

    @Provides
    @Singleton
    fun provideDatabase(application: Application, callback: MovieDatabase.Callback): MovieDatabase{
        return Room.databaseBuilder(application, MovieDatabase::class.java, "movies_database")
            .fallbackToDestructiveMigration()
            .addCallback(callback)
            .build()
    }


    @Provides
    fun provideMoviesDao(database: MovieDatabase): MovieDao {
        return database.getDao()
    }









}