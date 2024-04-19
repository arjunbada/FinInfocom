package com.test.fininfocom.database.repository

import android.content.Context
import com.test.fininfocom.database.AppDatabase
import com.test.fininfocom.database.dao.PersonDao
import com.test.fininfocom.details.model.Person

class PersonTRepository (context: Context) {
    var db: PersonDao = AppDatabase.getInstance(context)?.personDao()!!

    // Insert
    fun insert(person: MutableList<Person>): List<Long> {
        return db.insert(person)
    }

    // update
    fun update(person: Person): Int {
        return db.update(person)
    }


    //delete All
    fun deleteRecords() {
        return db.deleteRecords()
    }

    //Fetch All
    fun personsAll(): MutableList<Person>? {
        return db.personsAll()
    }

    //Fetch All by name
    fun getPersonsSortedByName(): MutableList<Person>? {
        return db.getPersonsSortedByName()
    }

    //Fetch All by age
    fun getPersonsSortedByAge(): MutableList<Person>? {
        return db.getPersonsSortedByAge()
    }

    //Fetch All by city
    fun getPersonsSortedByCity(): MutableList<Person>? {
        return db.getPersonsSortedByCity()
    }

}