package com.example.wikirando

import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_scrolling.*
import okhttp3.*
import java.io.IOException
import java.net.URL
import org.json.JSONObject
import android.R.string
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import kotlinx.android.synthetic.main.content_scrolling.*


class ScrollingActivity : AppCompatActivity() {
    private val client = OkHttpClient()
    lateinit var questionView: TextView
    private val tag = "MainActivity"
    var text: String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(tag, "onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)
        setSupportActionBar(toolbar)
        fab.setOnClickListener { view ->
            //Snackbar.make(view, "", Snackbar.LENGTH_LONG)
            // TODO Add link to database
        }
        //("https://en.wikipedia.org/wiki/Special:Random")

    }
    override fun onStart() {
        Log.d(tag, "OnStart")
        super.onStart()
        questionView = findViewById(R.id.scrollingText)
        val apiToCheck = getString(R.string.api_link)
        run(apiToCheck)
    }
    fun run(url: String)
    {
        Log.d(tag, "run")
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException)
            {
                Log.d("REEEE", e.toString())
            }
            override fun onResponse(call: Call, response: Response)
            {
                Log.d(tag, "onResponse")
                text = response.body()?.string()
                val resStr = text.toString()
                val jsonTitle = JSONObject(resStr).getString("title")
                val jsonSummary = JSONObject(resStr).getString("extract")
                Log.d(tag, jsonSummary)
                this@ScrollingActivity.runOnUiThread(java.lang.Runnable {
                    questionView.text = jsonTitle+"\n"+jsonSummary
                })
            }
        })
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_scrolling, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
