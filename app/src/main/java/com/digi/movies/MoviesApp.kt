package com.digi.movies

import androidx.multidex.MultiDexApplication

import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MoviesApp : MultiDexApplication() {


    override fun onCreate() {
        super.onCreate()


    }
}