package com.internshala.echo.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.internshala.echo.Songs

/**
 * Created by lenovo on 26/01/2018.
 */
class EchoDatabase:SQLiteOpenHelper{
    var _songList=ArrayList<Songs>()


    object Staticated{
        var DB_VERSION=1
        val DB_NAME= "favouriteDatabase"
        val TABLE_NAME="favouriteTable"
        val COLUMN_ID="songID"
        val COLUMN_SONG_TITLE="songTitle"
        val COLUMN_SONG_ARTIST="songArtist"
        val COLUMN_SONG_PATH="songPath"
    }


    override fun onCreate(sqlitedatabase: SQLiteDatabase?) {
        sqlitedatabase?.execSQL( "CREATE TABLE " + Staticated.TABLE_NAME + "(" + Staticated.COLUMN_ID + "INTEGER," + Staticated.COLUMN_SONG_ARTIST + "STRING,"
                + Staticated.COLUMN_SONG_TITLE + "STRING," + Staticated.COLUMN_SONG_PATH + "STRING);")


    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    constructor(context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int) : super(context, name, factory, version)

    constructor(context: Context?) : super(context, Staticated.DB_NAME, null, Staticated.DB_VERSION)

    fun storeAsFavourite(id: Int?, artist:String?,songTitle:String?,path:String?){
        val db=this.writableDatabase
        var contentValues= ContentValues()
        contentValues.put(Staticated.COLUMN_ID, id)
        contentValues.put(Staticated.COLUMN_SONG_ARTIST, artist)
        contentValues.put(Staticated.COLUMN_SONG_TITLE, songTitle)
        contentValues.put(Staticated.COLUMN_SONG_PATH, path)
        db.insert(Staticated.TABLE_NAME,null,contentValues)
        db.close()
    }

    fun queryDBList():ArrayList<Songs>?{
        try{
            val db=this.readableDatabase
            val query_params="SELECT * FROM " + Staticated.TABLE_NAME
            var cSor=db.rawQuery(query_params, null)
            if(cSor.moveToFirst()){
                do {
                    var _id=cSor.getInt(cSor.getColumnIndexOrThrow(Staticated.COLUMN_ID))
                    var _artist=cSor.getString(cSor.getColumnIndexOrThrow(Staticated.COLUMN_SONG_ARTIST))
                    var _title=cSor.getString(cSor.getColumnIndexOrThrow(Staticated.COLUMN_SONG_TITLE))
                    var _songPath=cSor.getString(cSor.getColumnIndexOrThrow(Staticated.COLUMN_SONG_PATH))
                    _songList.add(Songs(_id.toLong(),_artist,_title,_songPath,0))

                }while (cSor.moveToNext())

            }else{
                return null
            }

        }catch (e:Exception){
            e.printStackTrace()
        }

        return _songList
    }
    fun checkIfIdExixts(_id:Int):Boolean{
        var storeId=-1090
        val db=this.readableDatabase
        val query_params="SELECT * FROM " + Staticated.TABLE_NAME + "WHERE SongID= '$_id'"
        val cSor=db.rawQuery(query_params,null)
        if(cSor.moveToFirst()){
            do{
                storeId=cSor.getInt(cSor.getColumnIndexOrThrow(Staticated.COLUMN_ID))

            }while (cSor.moveToNext())

            }else{
            return false
        }
        return storeId !=-1090
    }

    fun deleteFavourite(_id: Int){
        val db=this.writableDatabase
        db.delete(Staticated.TABLE_NAME,Staticated.COLUMN_ID + "=" + _id, null)
        db.close()
    }
    fun checkSize():Int{
        var counter=0
        val db=this.readableDatabase
        var query_params ="SELECT * FROM " + Staticated.TABLE_NAME
        val cSor=db.rawQuery(query_params,null)
        if(cSor.moveToFirst()){
            do{
                counter=counter+1

            }while (cSor.moveToNext())
        }else{
            return 0
        }
        return counter

    }

}