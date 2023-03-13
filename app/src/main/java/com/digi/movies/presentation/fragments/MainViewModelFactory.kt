package com.digi.movies.presentation.fragments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.digi.movies.domain.repository.MainRepo
import com.digi.movies.presentation.fragments.main.MainViewModel
import javax.inject.Inject

class MainViewModelFactory @Inject constructor(private val mainRepo: MainRepo): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel( mainRepo = mainRepo ) as T
        }

        throw IllegalArgumentException("Unknown class name")

    }



}