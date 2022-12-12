package com.mek.besinlerkitabi.servis

import android.content.Context
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mek.besinlerkitabi.model.Besin

@Database(entities = arrayOf(Besin::class),version = 1)
abstract class BesinDataBase : RoomDatabase() {
    abstract fun besinDao() : BesinDAO
    companion object {
        @Volatile private var instance : BesinDataBase?= null
        private val lock = Any()
        operator fun invoke(context:Context) = instance?: synchronized(lock){
            instance?: databaseOlustur(context).also {
                instance = it
            }
        }

        private fun databaseOlustur(context: Context)= Room.databaseBuilder(context.applicationContext,BesinDataBase::class.java,"besindatabase").allowMainThreadQueries().build()

    }

}