package com.digi.movies.domain.repository

import com.digi.movies.domain.models.BaseGenre
import com.digi.movies.domain.models.BaseMovies
import com.digi.movies.domain.models.Genre
import com.digi.movies.domain.models.Movie
import kotlinx.coroutines.flow.Flow

interface MainRepo {

    fun getGenreRemote() : Flow<BaseGenre>
    fun getGenreLocal() : Flow<List<Genre>>

    fun getGenreListRemote(listId : String) : Flow<BaseMovies>
    fun getGenreListLocal(listId : String) : Flow<List<Movie>>

    fun getSearchQuery(listId : String) : Flow<BaseMovies>

    fun saveGenre(genre: List<Genre>) : Flow<List<Long>>
    fun saveMovie(movie: List<Movie>) : Flow<List<Long>>
}