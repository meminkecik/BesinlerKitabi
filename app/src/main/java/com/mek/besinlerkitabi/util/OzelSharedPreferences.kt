package com.mek.besinlerkitabi.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

open class OzelSharedPreferences{
    private val ZAMAN = "zaman"
    companion object {

        private var sharedPreferances : SharedPreferences? = null
        @Volatile private var instance : OzelSharedPreferences?= null
        private val  lock = Any()
        operator fun invoke(context: Context) : OzelSharedPreferences = instance?: synchronized(lock){
            instance?: ozelSharedPreferencesYap(context).also {
                instance = it
            }
        }


        private fun ozelSharedPreferencesYap(context: Context): OzelSharedPreferences{
            sharedPreferances = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
            return OzelSharedPreferences()
        }


    }
    fun zamaniKaydet(zaman : Long){
        sharedPreferances?.edit(commit = true){
            putLong(ZAMAN,zaman)
        }
    }
    fun zamaniAl() = sharedPreferances?.getLong(ZAMAN,0)
}