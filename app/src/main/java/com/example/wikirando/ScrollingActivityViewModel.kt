package com.example.wikirando

import android.widget.TextView
import androidx.lifecycle.ViewModel
import okhttp3.OkHttpClient


class ScrollingActivityViewModel : ViewModel() {
    var text: String? = ""
    var title: String? = ""
    var link: String? = ""
    var firstRun: Boolean = true
    var summary: String? = ""
    var fabEnabled: Boolean = false
    private val tag = "ScrollingActivityViewModel"

}