package com.webbisswift.cfcnepal.domain.localdb

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.webbisswift.cfcnepal.domain.localdb.dao.NewsDao
import com.webbisswift.cfcnepal.domain.localdb.entities.DBNewsItem
import com.webbisswift.cfcnepal.utils.SingletonHolder

/**
 * Created by apple on 12/12/17.
 */

@Database(entities = arrayOf(DBNewsItem::class), version = 1, exportSchema = false)
abstract class AppDatabase :RoomDatabase(){
    abstract fun newsDao() : NewsDao

    companion object : SingletonHolder<AppDatabase, Context>({
        Room.databaseBuilder(it.applicationContext,
                AppDatabase::class.java, "cfc_nepal_news.db")
                .build()
    })
}