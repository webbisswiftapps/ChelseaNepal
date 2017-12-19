package com.webbisswift.cfcn.domain.localdb.dao

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import com.webbisswift.cfcn.domain.localdb.entities.DBNewsItem

/**
 * Created by apple on 12/12/17.
 */
@Dao interface NewsDao{

    @Query("select * from news")
    fun getAllNews():List<DBNewsItem>

    @Query("select * from news where finalLink = :p0")
    fun findNewsItemByLink(p0: String): DBNewsItem

    @Insert(onConflict = REPLACE)
    fun insertNewsItem(item: DBNewsItem)

    @Update(onConflict = REPLACE)
    fun updateNewsItem(item: DBNewsItem)

    @Delete
    fun deleteNewsItem(item: DBNewsItem)

    @Query("delete from news")
    fun deleteAllNews()

}