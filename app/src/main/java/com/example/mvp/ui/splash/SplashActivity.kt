package com.example.mvp.ui.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.mvp.R
import kotlinx.android.synthetic.main.activity_main.*

class SplashActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Glide.with(applicationContext).load("https://ttol.vietnamnetjsc.vn/images/2018/05/25/13/40/net-cuoi-be-gai-5-1527053440031984418330.jpg").into(img)

    }
}