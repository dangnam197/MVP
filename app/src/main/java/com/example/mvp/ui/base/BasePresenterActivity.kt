package com.example.mvp.ui.base

import android.os.Bundle

@Suppress("UNCHECKED_CAST")
abstract class BasePresenterActivity<V : BaseView,P : BasePresenter<V>>:BaseActivity(){
    private var presenter:P? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = setPresenter()
        presenter?.binView(getViewLayer())
    }
    abstract fun setPresenter():P
    private fun getViewLayer():V{
        return this as V
    }
    fun getPresenter(): P? = presenter
    override fun onDestroy() {
        presenter?.onDestroy()
        super.onDestroy()
    }
}