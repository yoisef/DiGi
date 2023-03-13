package com.digi.movies.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.digi.movies.data.db.MovieDao
import com.digi.movies.data.db.MovieDatabase
import com.digi.movies.domain.models.Movie

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import com.google.common.truth.Truth.assertThat
import org.junit.Rule

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
@SmallTest
class MovieDaoTest {

    // to ensure all the code will execute
    @get:Rule
    var InstantTaskExecutorRule = androidx.arch.core.executor.testing.InstantTaskExecutorRule()

    private lateinit var toDoDatabase: MovieDatabase
    private lateinit var dao: MovieDao
    @Before
    fun setup()
    {
        toDoDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MovieDatabase::class.java
        ).build()

        dao=toDoDatabase.getDao()
    }

    @Test
    fun insertTask()= kotlinx.coroutines.test.runTest {


        val movie1=  Movie(1, true, "personal", null, 0, "0", "hy","io","k","io","j","h")
        val movie2=  Movie(2, true, "personal", null, 3, "0", "hy")
        val movies= listOf(movie1,movie2)
        dao.insertMovie(movies)
        val result = dao.getMovies()

       assertThat(result.size).isEqualTo(2)


    }

    @Test
    fun deleteMovieItem()= kotlinx.coroutines.test.runTest {

        val movie1=  Movie(1, true, "personal", null, 0, "0", "hy","io","k","io","j","h")
        val movie2=  Movie(2, true, "personal", null, 3, "0", "hy")
        val movies= listOf(movie1,movie2)
        dao.insertMovie(movies)
        dao.deleteAllMovies()
        val result = dao.getMovies()
        assertThat(result.size).isEqualTo(0)


    }

    @Test
    fun getMoviesByGenreId()= kotlinx.coroutines.test.runTest {

        val movie3=  Movie(3, true, "personal", null, 2, "0", "hy","io","k","io","j","h")
        val movie1=  Movie(1, true, "personal", null, 3, "0", "hy","io","k","io","j","h")
        val movie2=  Movie(2, true, "personal", null, 3, "0", "hy")
        val movies= listOf(movie1,movie2,movie3)
        dao.insertMovie(movies)
        val result =  dao.getMoviesByGener("2")

        assertThat(result.size).isEqualTo(1)


    }
    @Test()
    fun getAllMovies()= kotlinx.coroutines.test.runTest {


        val movie1=  Movie(1, true, "personal", null, 0, "0", "hy","io","k","io","j","h")
        val movie2=  Movie(2, true, "personal", null, 3, "0", "hy")
        val movies= listOf(movie1,movie2)

        dao.insertMovie(movies)


        val result = dao.getMovies()
        assertThat(result.size).isEqualTo(2)


    }




    }
