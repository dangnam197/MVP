package com.example.mvp.ui.main

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import androidx.core.content.ContextCompat
import com.androidnetworking.AndroidNetworking
import com.example.mvp.R
import com.example.mvp.data.db.AppDatabase
import com.example.mvp.data.db.model.Book
import com.example.mvp.data.network.ApiStory
import com.example.mvp.data.network.model.LinkChap
import com.example.mvp.data.network.model.Story
import com.example.mvp.ui.base.BasePresenter
import com.example.mvp.util.Constant
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class MainPresenter(context: Context) : BasePresenter<MainView>(context) {
    // private val s = "https://m.truyen.tangthuvien.vn/doc-truyen/26995/4441294"

    private val appDatabase: AppDatabase = AppDatabase.getInstance(context)

    private val apiStory: ApiStory

    private var appBarHeight: Int = -1

    private var stateShowAppBar = true

    private var style = Constant.LIGHT

    private var backGroundColor = ContextCompat.getColor(context, R.color.colorBackGroundTextLight)

    private var appBarBackgroundColor = ContextCompat.getColor(context, R.color.colorPrimary)

    private var textColor = ContextCompat.getColor(context, R.color.colorBackGroundTextLight)

    private var mLinkChap: LinkChap? = null

    private var textSize = 16f

    private var textToSpeech: TextToSpeech

    private var mOldOffset = 0

    private var mOffset = 0

    private var text: String = ""

    var touchLock: Boolean = false

    private val apiStoryCallBackListener = object : ApiStory.CallBack {
        override fun setStory(story: Story) {
            text = story.content
            getView()?.setStory(story)
            getView()?.hideProgress()
            appDatabase.BookDao().insertBook(
                Book(
                    story.storyId.toInt(),
                    story.title,
                    "https://m.truyen.tangthuvien.vn/doc-truyen/${story.storyId}/${story.chapId}"
                )
            )
                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe()
        }

        override fun setLinkChap(linkChap: LinkChap) {
            mLinkChap = linkChap
            getView()?.setLinkChap(linkChap)
        }
    }

    init {
        apiStory = ApiStory(apiStoryCallBackListener)
        AndroidNetworking.initialize(context)
        textToSpeech = TextToSpeech(context) {}

    }

    fun getStory(link: String) {
        getView()?.showProgress()
        ApiStory(apiStoryCallBackListener).getStory(link)
    }

    fun increaseTextSize() {
        if (textSize < 30) {
            textSize++
            getView()?.setTextSize(textSize)
        }
    }

    fun reduceTextSize() {
        if (textSize > 10) {
            textSize--
            getView()?.setTextSize(textSize)
        }
    }

    fun setStyle(style: Int) {
        this.style = style
        if (style == Constant.LIGHT) {
            backGroundColor = ContextCompat.getColor(context, R.color.colorBackGroundTextLight)
            textColor = ContextCompat.getColor(context, R.color.colorTextLight)
            getView()?.setStyle(textColor, backGroundColor)
        } else if (style == Constant.DARK) {
            backGroundColor = ContextCompat.getColor(context, R.color.colorBackGroundTextDark)
            textColor = ContextCompat.getColor(context, R.color.colorTextDark)
            getView()?.setStyle(textColor, backGroundColor)
        }
    }

    fun setAppBarOffset(p1: Int) {

        stateShowAppBar = if (p1 == -appBarHeight) {
            getView()?.setColorStatusBar(backGroundColor)
            getView()?.setElevationAppBar(0f)
            if (style == Constant.DARK) {
                getView()?.setStatusBarDark()
            }
            false
        } else {
            if (!stateShowAppBar) {
                getView()?.setColorStatusBar(appBarBackgroundColor)
                getView()?.setElevationAppBar(10f)
                if (style == Constant.DARK) {
                    getView()?.setStatusBarLight()
                }
            }
            true

        }
    }

    fun setAppBarHeight(measuredHeight: Int) {
        appBarHeight = measuredHeight
    }

    fun nextChap() {
        mLinkChap?.nextLink?.let { getStory(it) }
    }

    fun preChap() {
        mLinkChap?.preLink?.let { getStory(it) }
    }

    private fun next(start: Int) {
        mOldOffset = start
        mOffset = text.indexOf(arrayOf('.', '?', '\n'), start)
        if (mOffset != -1) {
            getView()?.setSpanTextScroll(mOldOffset, mOffset)
            speak(text.substring(mOldOffset, mOffset))

        }


    }

    fun speechClick() {

        next(mOffset)

    }

    private fun speak(text: String) {

        textToSpeech.speak(text, TextToSpeech.QUEUE_ADD, null, "textspeech")
        textToSpeech.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onDone(p0: String?) {
                next(mOffset + 1)
            }

            override fun onError(p0: String?) {

            }

            override fun onStart(p0: String?) {

            }
        })
    }

    private fun String.indexOf(chars: Array<Char>, start: Int): Int {
        for (i in start until this.length) {
            for (char in chars) {
                if (this[i] == char) {
                    return if (i < this.length - 1) {
                        i + 1
                    } else {
                        i
                    }

                }
            }

        }
        return -1
    }

    private fun String.indexOfPrevious(chars: Array<Char>, start: Int): Int {
        for (i in start downTo 0) {
            for (char in chars) {
                if (this[i] == char) {
                    return if (i < this.length - 1) {
                        i + 1
                    } else {
                        i
                    }
                }
            }

        }
        return 0
    }

    fun select(offset: Int) {
        if (touchLock) {
            mOldOffset = text.indexOfPrevious(arrayOf('.', '"', '?', '\t'), offset)
            mOffset = text.indexOf(arrayOf('.', '\n', '?', ':'), offset)
            speak(text.substring(mOldOffset, mOffset))
            getView()?.setSpanText(mOldOffset, mOffset)
        }
    }

}



