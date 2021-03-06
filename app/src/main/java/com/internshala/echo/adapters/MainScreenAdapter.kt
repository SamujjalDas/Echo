package com.internshala.echo.adapters

import android.content.Context
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.internshala.echo.R
import com.internshala.echo.Songs
import com.internshala.echo.activities.MainActivity
import com.internshala.echo.fragments.MainScreenFragment
import com.internshala.echo.fragments.SongPlayingFragment

/**
 * Created by lenovo on 14/01/2018.
 */
class MainScreenAdapter(_songDetails:ArrayList<Songs>, _context:Context): RecyclerView.Adapter<MainScreenAdapter.MyViewHolder>(){
    var songDetails:ArrayList<Songs>?=null
    var mContext:Context?=null
    init{
        this.songDetails=_songDetails
        this.mContext=_context
    }

    override fun onBindViewHolder(holder:MyViewHolder, position: Int) {
     val songObject=songDetails?.get(position)
        holder.trackTitle?.text=songObject?.SongTitle
        holder.trackArtist?.text=songObject?.artist
        holder.contentHolder?.setOnClickListener({
            val songPlayingFragment= SongPlayingFragment()
            var args=Bundle()
            args.putString("songArtist",songObject?.artist)
            args.putString("path",songObject?.songData)
            args.putString("songArtist",songObject?.SongTitle)
            args.putInt("songId",songObject?.songID?.toInt() as Int)
            args.putInt("songPosition",position)
            args.putParcelableArrayList("songData",songDetails)
            songPlayingFragment.arguments =args


            (mContext as FragmentActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.details_fragment,songPlayingFragment)
                    .addToBackStack("SongPlayingFragment")
                    .commit()
        })




    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int):MyViewHolder {
        val itemView = LayoutInflater.from(parent?.context)
                .inflate(R.layout.row_custom_mainscreen_adapter,parent, false)
        return MyViewHolder(itemView)


    }

    override fun getItemCount(): Int {
        if (songDetails == null) {
            return 0
        } else {
            return (songDetails as ArrayList<Songs>).size
        }



    }

        class MyViewHolder(view: View):RecyclerView.ViewHolder(view){
            var trackTitle=view.findViewById<TextView>(R.id.trackTitle)
            var trackArtist=view.findViewById<TextView>(R.id.trackArtist)
            var contentHolder=view.findViewById<RelativeLayout>(R.id.contentRow)

        }
    }
