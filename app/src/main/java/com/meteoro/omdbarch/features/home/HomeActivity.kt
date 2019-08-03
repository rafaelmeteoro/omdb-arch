package com.meteoro.omdbarch.features.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.meteoro.omdbarch.OmdbApplication
import com.meteoro.omdbarch.R
import com.meteoro.omdbarch.di.ViewModelFactory
import javax.inject.Inject

class HomeActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()
        setContentView(R.layout.activity_home)
    }

    private fun inject() {
        OmdbApplication.appCompoent.inject(this)
    }
}