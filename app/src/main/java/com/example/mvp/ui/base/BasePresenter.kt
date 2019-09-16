package com.example.mvp.ui.base

import android.content.Context

abstract class BasePresenter<V : BaseView> internal constructor(var context: Context){
    private var view:V? = null
    fun getView(): V? = view
    fun binView(view : V){
        this.view = view
    }
    fun onDestroy(){
        view = null
    }
}