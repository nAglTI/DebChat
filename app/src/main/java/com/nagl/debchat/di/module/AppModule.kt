package com.nagl.debchat.di.module

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.nagl.debchat.data.source.net.retrofit.AuthInterceptor
import com.nagl.debchat.di.intercept.AuthInterceptorOkHttpClient
import com.nagl.debchat.di.intercept.LoggerInterceptorOkHttpClient
import com.nagl.debchat.utils.Urls
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(): AuthInterceptor {
        return AuthInterceptor()
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    @AuthInterceptorOkHttpClient
    fun provideAuthOkHttpClient(interceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    @Provides
    @Singleton
    @LoggerInterceptorOkHttpClient
    fun provideLoggingOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    @Provides
    @Singleton
    //@AuthRetrofit
    fun provideAuthRetrofitBuilder(
        @AuthInterceptorOkHttpClient client: Lazy<OkHttpClient>,
        converterFactory: GsonConverterFactory,
    ): Retrofit {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(Urls.BASE_URL)
            .client(client.get())
            .addConverterFactory(converterFactory)
        return retrofitBuilder.build()
    }

//    @Provides
//    @Singleton
//    //@LoggerRetrofit
//    fun provideLogRetrofitBuilder(
//        @LoggerInterceptorOkHttpClient client: Lazy<OkHttpClient>,
//        converterFactory: GsonConverterFactory,
//        context: Context
//    ): Retrofit {
//        val retrofitBuilder = Retrofit.Builder()
//            .baseUrl(Urls.BASE_URL)
//            .client(client.get())
//            .addConverterFactory(converterFactory)
//        return retrofitBuilder.build()
//    }

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }
}