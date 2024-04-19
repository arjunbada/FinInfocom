package com.test.fininfocom.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.test.fininfocom.database.dao.PersonDao
import com.test.fininfocom.details.model.Person
import com.test.fininfocom.utils.Constants

@Database(
    entities = [Person::class],
    version = 183,
    exportSchema = true
)

abstract class AppDatabase : RoomDatabase() {

    abstract fun personDao(): PersonDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    val builder = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        Constants.DB_NAME
                    ).allowMainThreadQueries()
                    builder.fallbackToDestructiveMigration()
                    INSTANCE = builder.build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}