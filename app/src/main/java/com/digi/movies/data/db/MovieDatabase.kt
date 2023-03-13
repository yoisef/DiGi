package com.digi.movies.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.digi.movies.di.ApplicationScope
import com.digi.movies.domain.models.Genre
import com.digi.movies.domain.models.Movie
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject


@Database(entities = [Movie::class,Genre::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun getDao(): MovieDao

    class Callback @Inject constructor(
        private val database: javax.inject.Provider<MovieDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback()





}