package com.internshala.echo

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by lenovo on 14/01/2018.
 */
class Songs(var songID:Long, var SongTitle:String, var artist:String, var songData:String,var DateAdded:Long):Parcelable{
    override fun writeToParcel(p0: Parcel?, p1: Int) {
     }


    override fun describeContents(): Int {
        return 0
    }
    object Statified{
        var nameComparator:Comparator<Songs> = Comparator<Songs>{song1,song2 ->
            val songOne = song1.SongTitle.toUpperCase()
            val song2=song2.SongTitle.toUpperCase()
            songOne.compareTo(song2)

        }

        var dateComparator:Comparator<Songs> = Comparator<Songs>{song1,song2 ->
            val songOne=song1.DateAdded.toDouble()
            val songTwo=song2.DateAdded.toDouble()
            songTwo.compareTo(songOne)
        }
    }


}