package com.nagl.debchat.di.intercept

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthInterceptorOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LoggerInterceptorOkHttpClient

//@Qualifier
//@Retention(AnnotationRetention.BINARY)
//annotation class LoggerRetrofit
//
//@Qualifier
//@Retention(AnnotationRetention.BINARY)
//annotation class AuthRetrofit