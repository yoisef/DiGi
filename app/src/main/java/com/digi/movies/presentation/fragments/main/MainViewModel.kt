package com.digi.movies.presentation.fragments.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digi.movies.domain.models.BaseGenre
import com.digi.movies.domain.models.BaseMovies
import com.digi.movies.domain.models.Genre
import com.digi.movies.domain.models.Movie
import com.digi.movies.domain.repository.MainRepo
import com.digi.movies.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepo: MainRepo) : ViewModel() {


    private val _getGenres = MutableStateFlow<Resource<BaseGenre>>(Resource.error(null, ""))
    val getGenres: StateFlow<Resource<BaseGenre>> = _getGenres

    private val _getLocalGenres = MutableStateFlow<Resource<List<Genre>>>(Resource.error(null, ""))
    val getLocalGenres: StateFlow<Resource<List<Genre>>> = _getLocalGenres

    private val _getMoviesListR = MutableStateFlow<Resource<BaseMovies>>(Resource.error(null, ""))
    val getMoviesListR: StateFlow<Resource<BaseMovies>> = _getMoviesListR

    private val _getMoviesListL = MutableStateFlow<Resource<List<Movie>>>(Resource.error(null, ""))
    val getMoviesListL: StateFlow<Resource<List<Movie>>> = _getMoviesListL

    private val _getSearchQuery = MutableStateFlow<Resource<BaseMovies>>(Resource.error(null, ""))
    val getSearchQuery: StateFlow<Resource<BaseMovies>> = _getSearchQuery

    fun getGenreRemote() {
        viewModelScope.launch {
            _getGenres.emit(Resource.loading(null))
            try {
            mainRepo.getGenreRemote().collect {

                if (it.genres.isNotEmpty()) {
                    _getGenres.emit(Resource.success(it))
                } else {
                    _getGenres.emit(Resource.error(null, "Categories Is Empty"))

                }

            }


            }catch (e: Exception)
            {
                _getGenres.emit(Resource.error(null, "No Internet Connection"))

            }


        }


    }
    fun getGenreLocal() {
        viewModelScope.launch {
            _getLocalGenres.emit(Resource.loading(null))

                mainRepo.getGenreLocal().collect {

                    if (it.isNotEmpty()) {
                        _getLocalGenres.emit(Resource.success(it))
                    } else {
                        _getLocalGenres.emit(Resource.error(null, "Categories Is Empty"))
                    }
                }




        }


    }

    fun savedMovie(movie: List<Movie>)
    {
        viewModelScope.launch {

            try {
                mainRepo.saveMovie(movie).collect()

            }catch (e : Exception)
            {
                Log.e("error_insertm","=="+e.toString())

            }
        }
    }
    fun savedGenre(genre: List<Genre>)
    {
        viewModelScope.launch {


                mainRepo.saveGenre(genre).collect()


        }
    }
    fun getGenreLocaleList(listId : String) {
        viewModelScope.launch {
            _getMoviesListL.emit(Resource.loading(null))
            mainRepo.getGenreListLocal(listId).collect {

                if (it.isNotEmpty()) {
                    _getMoviesListL.emit( Resource.success(it))
                } else {
                    _getMoviesListL.emit(Resource.error(it, "Categories Is Empty"))

                }
            }

        }
    }

    fun getGenreList(listId: String) {
        viewModelScope.launch {

            _getMoviesListR.emit(Resource.loading(null))
            mainRepo.getGenreListRemote(listId).collect {

                try {
                    if (it.results.isNotEmpty()) {
                        _getMoviesListR.emit(Resource.success(it))
                    } else {
                        _getMoviesListR.emit( Resource.error(null, "Properties Is Empty"))

                    }

                } catch (e: java.lang.Exception) {
                    Resource.error(null, "NetWork Error")
                }

            }

        }
    }

    fun getSearchQuery(query: String) {
        viewModelScope.launch {
            _getSearchQuery.emit(Resource.loading(null))

            mainRepo.getSearchQuery(query).collect {
                if (it.results.isNotEmpty()) {
                    _getSearchQuery.value = Resource.success(it)
                } else {
                    _getSearchQuery.value = Resource.error(it, "Search Is Empty")

                }
            }
        }
    }
}