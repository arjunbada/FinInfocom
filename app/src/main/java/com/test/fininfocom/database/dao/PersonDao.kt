package com.test.fininfocom.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.test.fininfocom.details.model.Person

@Dao
interface PersonDao {
    @Insert
    fun insert(person: MutableList<Person>): List<Long>

    @Update
    fun update(person: Person): Int

    @Query("DELETE FROM person_table")
    fun deleteRecords()

    @Query("SELECT * FROM person_table")
    fun personsAll(): MutableList<Person>?

    @Query("SELECT * FROM person_table ORDER BY name ASC")
    fun getPersonsSortedByName(): MutableList<Person>?


    @Query("SELECT * FROM person_table ORDER BY age ASC")
    fun getPersonsSortedByAge(): MutableList<Person>?

    @Query("SELECT * FROM person_table ORDER BY city ASC")
    fun getPersonsSortedByCity(): MutableList<Person>?

}