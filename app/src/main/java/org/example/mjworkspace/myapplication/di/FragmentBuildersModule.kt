package org.example.mjworkspace.myapplication.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import org.example.mjworkspace.myapplication.fragments.v.NowFragment

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeNowFragment(): NowFragment
}