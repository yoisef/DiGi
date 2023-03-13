package com.digi.movies.data.db

import androidx.room.*
import com.digi.movies.domain.models.Genre

import com.digi.movies.domain.models.Movie


@Dao
interface MovieDao {


    @Query("SELECT * FROM movies")
    suspend fun getMovies() : List<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movies : List<Movie>) : List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenre(listgener : List<Genre>) : List<Long>

    @Query("SELECT * FROM genre")
    suspend fun getGenres() : List<Genre>

    @Query("SELECT * FROM movies WHERE genre_id LIKE :generId ")
    suspend fun getMoviesByGener( generId : String) : List<Movie>

    @Query("DELETE FROM movies")
    suspend fun deleteAllMovies()


    @Query("SELECT * FROM movies WHERE title LIKE :name || '%'")
    suspend fun searchMovie(name:String): Movie


}