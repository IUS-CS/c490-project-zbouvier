package com.example.wikirando

import android.widget.TextView
import androidx.lifecycle.ViewModel
import okhttp3.OkHttpClient


class ScrollingActivityViewModel : ViewModel() {
    private lateinit var db: AppDatabase
    var text: String? = ""
    var title: String? = ""
    var link: String? = ""
    var fabEnabled: Boolean = false
    private val client = OkHttpClient()
    lateinit var questionView: TextView
    private val tag = "ScrollingActivityViewModel"
}