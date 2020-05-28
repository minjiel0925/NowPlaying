package org.example.mjworkspace.myapplication.di

import javax.inject.Qualifier


@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class MovieApi

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class CoroutineScopeIO