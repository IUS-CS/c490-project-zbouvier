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
import android.content.DialogInterface
import android.net.Uri
import androidx.appcompat.app.AlertDialog


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
        //val listItem = db.linkDao().getAll()
        val adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_list_item_1,myListOfTitles
        )
        listView.adapter = adapter

        listView.setOnItemClickListener(OnItemClickListener { adapter, view, position, arg ->
            val literals = arrayOf("January", "February", "March")
            val builder = AlertDialog.Builder(this@YourFavorites)

            // Set the alert dialog title
            builder.setTitle(myListOfTitles[position])

            // Set a positive button and its click listener on alert dialog
            builder.setPositiveButton("View"){_,_ ->
                // Do something when user press the positive button
                val openURL = Intent(Intent.ACTION_VIEW)
                openURL.data = Uri.parse(myListOfUrls[arg.toInt()])
                startActivity(openURL)
            }
            // Display a neutral button on alert dialog
            builder.setNeutralButton("Cancel"){_,_ ->
                Toast.makeText(applicationContext,"Action cancelled.",Toast.LENGTH_SHORT).show()
            }

            // Finally, make the alert dialog using builder
            val dialog: AlertDialog = builder.create()

            // Display the alert dialog on app interface
            dialog.show()

        }
        )
    }

}
