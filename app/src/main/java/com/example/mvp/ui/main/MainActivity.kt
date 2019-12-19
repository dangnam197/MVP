package com.example.mvp.ui.main

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Build.VERSION
import android.os.Bundle
import android.text.Spannable
import android.text.style.ForegroundColorSpan
import android.view.*
import android.widget.TextView
import com.example.mvp.R
import com.example.mvp.data.network.model.LinkChap
import com.example.mvp.data.network.model.Story
import com.example.mvp.ui.SelectTruyenActivity
import com.example.mvp.ui.base.BasePresenterActivity
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BasePresenterActivity<MainView, MainPresenter>(), MainView {

    private var height: Int = 0

    private var line: Int = 0

    private var scrollY = 0

    private val span = ForegroundColorSpan(Color.RED)

    private lateinit var gestureDetector: GestureDetector

    private val mGestureListener = object : GestureDetector.OnGestureListener {
        override fun onShowPress(p0: MotionEvent?) {

        }

        override fun onSingleTapUp(p0: MotionEvent?): Boolean {
            p0?.let {
                val offset = tv_name.getOffsetForPosition(p0.x, p0.y)
                getPresenter()?.select(offset)
            }
            return true
        }

        override fun onDown(p0: MotionEvent?): Boolean = true

        override fun onFling(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean =
            false

        override fun onScroll(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean =
            false

        override fun onLongPress(p0: MotionEvent?) {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        initView()
        initAction()
        initIntent()
//        getPresenter()?.getStory("https://m.truyen.tangthuvien.vn/doc-truyen/than-van-tien-vuong/chuong-146")


    }

    private fun initIntent() {
        intent?.let {
            var link :String? = intent.getStringExtra("LINK")
            if (link != null) {
                if(link.isNotEmpty()) {
                    getPresenter()?.getStory(link)
                }
            }
        }

    }

    private fun initView() {
        gestureDetector = GestureDetector(applicationContext,mGestureListener)
        scrollView2.viewTreeObserver.addOnGlobalLayoutListener {
            height = scrollView2.measuredHeight
        }
        if (VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    private fun initAction() {
        appBarLayout.viewTreeObserver.addOnGlobalLayoutListener {
            getPresenter()?.setAppBarHeight(appBarLayout.measuredHeight)
        }
        appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, p1 ->
            getPresenter()?.setAppBarOffset(p1)
        })

        previous_chap.setOnClickListener { getPresenter()?.preChap() }

        next_chap.setOnClickListener { getPresenter()?.nextChap() }

        increase_text_size.setOnClickListener { getPresenter()?.increaseTextSize() }

        reduce_text_size.setOnClickListener { getPresenter()?.reduceTextSize() }

        tv_name.setOnTouchListener { _, p1 -> gestureDetector.onTouchEvent(p1) }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_setting, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.setting -> {
                val dialog = DialogFragmentSetting.newInstance(window.decorView.measuredWidth, 200)
                getPresenter()?.let { dialog.setPresenter(it) }
                dialog.show(supportFragmentManager, "aaa")

            }
            R.id.text_to_speech -> getPresenter()?.speechClick()

            R.id.web_select -> {
                val intent =  Intent(this,SelectTruyenActivity::class.java)
                intent.putExtra("link",getPresenter()?.link)
                startActivity(intent)
                getPresenter()?.stopTTS()
            }
            R.id.web_select1 -> {
                val intent =  Intent(this,SelectTruyenActivity::class.java)
                intent.putExtra("link","https://m.truyen.tangthuvien.vn/")
                startActivity(intent)
                getPresenter()?.stopTTS()
            }
            R.id.web_select2 -> {
                val intent =  Intent(this,SelectTruyenActivity::class.java)
                intent.putExtra("link","https://wikidich.com")
                startActivity(intent)
                getPresenter()?.stopTTS()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setColorStatusBar(color: Int) {
        window.statusBarColor = color
    }

    override fun setPresenter(): MainPresenter {
        return MainPresenter(applicationContext)
    }

    override fun getLayoutResource(): Int {
        return R.layout.activity_main
    }

    override fun setElevationAppBar(elevation: Float) {
        appBarLayout.elevation = elevation
    }

    override fun setStyle(textColor: Int, backGroundColor: Int) {
        tv_name.setTextColor(textColor)
        scrollView2.setBackgroundColor(backGroundColor)
    }

    override fun setTextSize(textSize: Float) {
        tv_name.textSize = textSize
    }

    override fun setStatusBarLight() {
        if (VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    override fun setStatusBarDark() {
        if (VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility =
                window.decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        }
    }

    override fun setSpanText(start: Int , end: Int) {
        (tv_name.text as Spannable).run {
            setSpan(
                span,
                start,
                end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }

    override fun setSpanTextScroll(start: Int, end: Int) {
        line = tv_name.layout.getLineForOffset(end)
        scrollY = tv_name.layout.getLineTop(line)
        setSpanText(start,end)
        scrollView2.scrollTo(0, scrollY - height / 2)
    }

    override fun setStory(story: Story) {
        tv_name.setText(story.content, TextView.BufferType.SPANNABLE)
        toolbar.title = story.title

    }

    override fun setLinkChap(linkChap: LinkChap) {

    }
}


//btn_show_dialog.setOnClickListener {
//            toolbar.scrollY = 0
//            appBarLayout.scrollY = 0
//
//
//            //Snackbar.make(main,"click",Snackbar.LENGTH_LONG).show()
//            appDatabase.BookDao().findById(26995)
//
//                .observeOn(AndroidSchedulers.mainThread())
//
//                .subscribeOn(Schedulers.io())
//                .subscribe { t: Book? ->
//                    tv_name.text = t?.name
//                    Snackbar.make(main, "click ${t?.name}", Snackbar.LENGTH_LONG).show()
//                }
//
//
//        }
