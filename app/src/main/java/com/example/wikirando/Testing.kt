package com.example.wikirando

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wikirando.ui.testing.TestingFragment

class Testing : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.testing_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, TestingFragment.newInstance())
                .commitNow()
        }
    }

}
