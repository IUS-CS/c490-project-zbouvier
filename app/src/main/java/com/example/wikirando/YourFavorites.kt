package com.example.wikirando

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room

import kotlinx.android.synthetic.main.activity_favorites.*
import kotlinx.android.synthetic.main.content_favorites.*
import android.widget.AdapterView.OnItemClickListener
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.View
import android.widget.*
import android.widget.Toast
import android.widget.TextView
import android.widget.AdapterView
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.net.Uri


class YourFavorites : AppCompatActivity() {
    private lateinit var db: AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)
        setSupportActionBar(toolbar)
        var myListOfUrls: MutableList<String> = mutableListOf<String>()
        var myListOfTitles: MutableList<String> = mutableListOf<String>()
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "link_table"
        ).allowMainThreadQueries().build()
        var listOfDatabase = db.linkDao().getAll()
        listOfDatabase.forEach {
            Log.d("zbouvier", it.toString())
            myListOfUrls.add(it.linkUrl!!)
            myListOfTitles.add(it.linkTitle!!)
        }
        val listItem = db.linkDao().getAll()
        val adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_list_item_1,myListOfTitles
        )

        listView.adapter = adapter
        listView.setOnItemClickListener(OnItemClickListener { adapter, view, position, arg ->
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse(myListOfUrls[arg.toInt()])
            startActivity(openURL)
        }
        )
    }

}
