package com.example.mvp.ui.base

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mvp.R

abstract class BaseActivity : AppCompatActivity(), BaseView {
    private var dialog :Dialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResource())
    }

    abstract fun getLayoutResource():Int

    override fun showProgress() {
        hideProgress()
        if (dialog == null) {
            dialog = showLoadingDialog()
        }
        dialog?.show()
    }

    private fun showLoadingDialog(): Dialog {
        val progressDialog = Dialog(this)
        progressDialog.let {
            it.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            it.setContentView(R.layout.progress_dialog)
            it.setCancelable(true)
            it.setCanceledOnTouchOutside(true)
            return it
        }
    }


    override fun hideProgress() {
        if (dialog != null && dialog!!.isShowing)
            dialog?.dismiss()
    }

    override fun setProgress(show: Boolean) {
        if(show){
            showProgress()
        }else{
            hideProgress()
        }
    }

    override fun showMessage(message: String) {

    }

}