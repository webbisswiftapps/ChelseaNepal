package com.webbisswift.cfcnepal.domain.localdb.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.webbisswift.cfcnepal.utils.Utilities
import org.joda.time.DateTime

/**
 * Created by apple on 12/12/17.
 */

//title:String, val thumbURL:String, val authorName:String, val publistDT:DateTime, val isVideo:Boolean, val finalLink:String, val isHeading:Boolean
@Entity(tableName = "news")
data class DBNewsItem(@ColumnInfo(name = "title") var title: String,
                @ColumnInfo(name="thumbURL") var thumbURL:String,
                @ColumnInfo(name="authorName") var authorName:String,
                @ColumnInfo(name="publishDT") var publishDate:String,
                @ColumnInfo(name="isVideo") var isVideo:Boolean = false,
                @PrimaryKey @ColumnInfo(name = "finalLink") var finalLink: String,
                @ColumnInfo(name="isHeading") var isHeading:Boolean){

    override fun equals(other: Any?): Boolean {
        val otherAsDBIte = other as DBNewsItem
        if(otherAsDBIte != null){
            return (this.finalLink.contentEquals(otherAsDBIte.finalLink))
        }else return false
    }

    fun getPubDate(): DateTime? {
        try {
            return Utilities.parseRFC822Date(this.publishDate)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    override fun toString(): String {
        return title + "  |  "+publishDate+"  | "+authorName+"\n"
    }


}