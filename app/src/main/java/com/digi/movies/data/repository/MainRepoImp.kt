package com.digi.movies.data.repository

import com.digi.movies.data.db.MovieDao
import com.digi.movies.domain.models.BaseGenre
import com.digi.movies.domain.models.BaseMovies
import com.digi.movies.domain.models.Genre
import com.digi.movies.domain.models.Movie
import com.digi.movies.domain.repository.MainRepo
import com.digi.movies.network.EndPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepoImp @Inject constructor(private val endPoint: EndPoint?,private val movieDao: MovieDao) : MainRepo {


    override fun getGenreRemote(): Flow<BaseGenre> =
        flow {
            endPoint?.let {
                emit(it.getGenre())
            }
        }

    override fun getGenreLocal(): Flow<List<Genre>> =

        flow {

            movieDao.let {
                emit(it.getGenres())
            }

        }



    override fun getGenreListRemote(listId: String): Flow<BaseMovies> =
        flow {
            endPoint?.let {
                emit(it.getGenreList(genreID = listId, "1",))
            }
        }

    override fun getGenreListLocal(genreId: String): Flow<List<Movie>> =

        flow {
          emit(movieDao.getMoviesByGener(genreId))

        }





    override fun getSearchQuery(query: String): Flow<BaseMovies> =
        flow {
            endPoint?.let {
                emit(it.searchMovie(query))
            }
        }





    override fun saveGenre(genre: List<Genre>) : Flow<List<Long>> =
        flow {

            emit(movieDao.insertGenre(genre))
        }




    override fun saveMovie(movie: List<Movie>) : Flow<List<Long>>  =
        flow {

            emit(movieDao.insertMovie(movie))
        }








}









