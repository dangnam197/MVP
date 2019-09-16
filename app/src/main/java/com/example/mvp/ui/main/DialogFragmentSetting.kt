package com.example.mvp.ui.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.example.mvp.R
import com.example.mvp.util.Constant
import kotlinx.android.synthetic.main.dialog_setting.*


class DialogFragmentSetting : DialogFragment() {
    private var mWidth = 0
    private var mHeight = 0
    private var mainPresenter: MainPresenter? = null
    private var checkViewLoad = false
    companion object {
        fun newInstance(width: Int, height: Int): DialogFragmentSetting {
            val dialogFragmentSetting = DialogFragmentSetting()
            val bundle = Bundle()
            bundle.putInt("WIDTH", width)
            bundle.putInt("HEIGHT", height)
            dialogFragmentSetting.arguments = bundle
            return dialogFragmentSetting

        }
    }

    fun setPresenter(mainPresenter: MainPresenter) {
        this.mainPresenter = mainPresenter
        if(checkViewLoad){
            touch_lock.isChecked = mainPresenter.touchLock
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, android.R.style.Theme_Material_Light_Dialog_Alert)
        arguments?.let {
            mWidth = it.getInt("WIDTH")
            mHeight = it.getInt("HEIGHT")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_setting, container)
        dialog!!.window!!.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM)
        val layoutParams: WindowManager.LayoutParams = WindowManager.LayoutParams()
        layoutParams.width = mWidth
        layoutParams.height = mHeight
        dialog!!.window!!.attributes = layoutParams
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        button_style_light.setOnClickListener {
            mainPresenter?.setStyle(Constant.LIGHT)
            dismiss()
        }
        button_style_dark.setOnClickListener {
            mainPresenter?.setStyle(Constant.DARK)
            dismiss()
        }
        mainPresenter?.let {
            touch_lock.isChecked = it.touchLock
        }
        touch_lock.setOnClickListener { mainPresenter?.let { it.touchLock =!it.touchLock  } }
    }
}