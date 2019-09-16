package com.example.mvp.ui.base

interface BaseView {
    fun showProgress()

    fun hideProgress()

    fun setProgress(show: Boolean)

    fun showMessage(message: String)
}