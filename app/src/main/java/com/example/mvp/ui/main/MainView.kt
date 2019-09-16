package com.example.mvp.ui.main

import com.example.mvp.data.network.model.LinkChap
import com.example.mvp.data.network.model.Story
import com.example.mvp.ui.base.BaseView

interface MainView : BaseView {
    fun setStory(story: Story)
    fun setLinkChap(linkChap: LinkChap)
    fun setColorStatusBar(color: Int)
    fun setElevationAppBar(elevation:Float)
    fun setStyle(textColor:Int,backGroundColor:Int)
    fun setTextSize(textSize:Float)
    fun setStatusBarLight()
    fun setStatusBarDark()
    fun setSpanText(start:Int,end:Int)
    fun setSpanTextScroll(start:Int,end:Int)
}