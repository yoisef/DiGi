package com.digi.movies.network


import com.digi.movies.domain.models.BaseGenre
import com.digi.movies.domain.models.BaseMovies
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface EndPoint {


    @GET("genre/movie/list")
   suspend fun getGenre( ): BaseGenre


    @GET("genre/{genre_id}/movies?")
    suspend fun getGenreList(@Path("genre_id") genreID: String?, @Query("page") currentPage: String?): BaseMovies

    @GET("search/movie?")
    suspend fun searchMovie(@Query("query") searchQuery: String?, @Query("page") currentPage: String?="1",@Query("include_adult") adult :Boolean=false): BaseMovies

}