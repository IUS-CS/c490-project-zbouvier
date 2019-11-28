package com.example.wikirando

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_scrolling.*
import okhttp3.*
import java.io.IOException
import org.json.JSONObject
import androidx.room.Room
import java.lang.Exception


class ScrollingActivity : AppCompatActivity() {
    private val client = OkHttpClient()
    lateinit var questionView: TextView
    private val tag = "MainActivity"
    private lateinit var db: AppDatabase
    val viewModel: ScrollingActivityViewModel by lazy {
        ViewModelProviders.of(this).get(ScrollingActivityViewModel::class.java)
    }
    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

         db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "link_table"
        ).allowMainThreadQueries().build()
        Log.d(tag, db.linkDao().getAll().toString())
        Log.d(tag, "onCreate")
        setContentView(R.layout.activity_scrolling)
        fab.setOnClickListener { _ ->
            viewModel.fabEnabled = !viewModel.fabEnabled
            if(viewModel.fabEnabled)
            {
                fab.setImageResource(android.R.drawable.star_big_on)
            }
            else
            {
                fab.setImageResource(android.R.drawable.star_big_off)
            }

            var newLink = Link(null, viewModel.title!!, viewModel.link!!)
            try
            {
                var doesThisWork = (db.linkDao().getByUrl(viewModel.link).linkUrl == "")
                Log.d(tag, "$doesThisWork $title: ${viewModel.link} already exists!")
            }
            catch(e: Exception)
            {
                db.linkDao().insertAll(newLink)
                Log.d(tag, "$title: ${viewModel.link} inserted!")
            }
            //Log.d(tag, link!!)
            //Log.d(tag, db.linkDao().getAll().toString())
        }
        refreshButton.setOnClickListener { _ ->
            val apiToCheck = getString(R.string.api_link)
            run(apiToCheck)
            //populateWithTestData(db)
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
        viewModel.fabEnabled = false
        fab.setImageResource(android.R.drawable.star_big_off);
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
                viewModel.text = response.body()?.string()
                val resStr = viewModel.text.toString()
                val jsonTitle = JSONObject(resStr).getString("title")
                val jsonSummary = JSONObject(resStr).getString("extract")
                val contentUrls = JSONObject(resStr).getString("content_urls")
                val mobileData = JSONObject(contentUrls).getString("mobile")
                viewModel.link = JSONObject(mobileData).getString("page")
                viewModel.title = jsonTitle
                Log.d(tag, jsonSummary)
                this@ScrollingActivity.runOnUiThread(java.lang.Runnable {
                    questionView.text = jsonTitle+"\n\n\n"+jsonSummary
                    //Log.d(tag, db.linkDao().getAll().toString())
                })
            }
        })
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_scrolling, menu)
        return true
    }
    private fun addLink(db: AppDatabase, user: Link): Link {
        db.linkDao().insertAll(user)
        return user
    }
    private fun populateWithTestData(db: AppDatabase) {
        var user = Link(null,"Testing", "ree.com")

        addLink(db, user)
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
