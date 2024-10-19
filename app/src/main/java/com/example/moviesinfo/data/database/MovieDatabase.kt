package com.example.moviesinfo.data.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.moviesinfo.data.models.Movie

@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {

    companion object {
        private var instance: MovieDatabase? = null
        private const val DB_NAME = "movie.db"

        fun getInstance(application: Application): MovieDatabase {
            if (instance == null) {
                synchronized(MovieDatabase::class) {
                    instance = buildDataBase(application)
                }
            }
            return instance!!
        }

        private fun buildDataBase(application: Application): MovieDatabase {
            return Room.databaseBuilder(
                application.applicationContext,
                MovieDatabase::class.java,
                DB_NAME
            ).build()
        }
    }

    abstract fun movieDao(): MovieDao

}