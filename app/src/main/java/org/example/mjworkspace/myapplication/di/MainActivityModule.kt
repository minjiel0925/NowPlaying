package org.example.mjworkspace.myapplication.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import org.example.mjworkspace.myapplication.MainActivity

@Suppress("unused")
@Module
abstract class MainActivityModule {
    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeMainActivity(): MainActivity
}