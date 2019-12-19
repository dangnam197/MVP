package com.example.mvp.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.mvp.R
import com.example.mvp.data.db.AppDatabase
import com.example.mvp.data.db.model.Book
import com.example.mvp.ui.main.MainActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.activity_main.*

class SplashActivity : AppCompatActivity(){
    private val callBack = object :ListAdpater.CallBack{
        override fun onClick(book: Book) {
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.putExtra("LINK",book.link)
            startActivity(intent)
        }

    }
    private var  listBook = ArrayList<Book>()
    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        list.layoutManager = LinearLayoutManager(applicationContext)
        val appDatabase: AppDatabase = AppDatabase.getInstance(applicationContext)
         appDatabase.BookDao().getAll().observeOn(AndroidSchedulers.mainThread())
             .subscribeOn(Schedulers.io())
             .subscribe { t-> list.adapter = ListAdpater(t as ArrayList<Book>,callBack)}


    }
}