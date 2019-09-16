package com.example.mvp.data.network

import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.StringRequestListener
import com.example.mvp.data.network.model.LinkChap
import com.example.mvp.data.network.model.Story
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class ApiStory(private var callBack: CallBack) : StringRequestListener {
    override fun onResponse(response: String?) {


        val document: Document = Jsoup.parse(response)
        val elements = document.select("a.nav-converter")
        val elementContents = document.getElementsByClass("content-block")
        val elementTitle = document.select(".page-title.text-center").last()
        val element = elements.last()
        val link = element.attr("href")
        val splitId = link.split('/')

        val storyId = splitId[4]
        val chapId = splitId[5]
        val title = elementTitle.ownText()
        var content = ""
        for (contentElement in elementContents) {
            content += "\t\t ${contentElement.ownText()} \n\n"
        }
        callBack.setStory(Story(title, content, storyId, chapId))
        getLink(storyId,chapId)


    }

    override fun onError(anError: ANError?) {

    }

    interface CallBack {
        fun setStory(story: Story)
        fun setLinkChap(linkChap: LinkChap)
    }

    fun getStory(link: String) {
        AndroidNetworking.get(link)
            .addHeaders(
                "User-Agent",
                "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.132 Mobile Safari/537.36"
            )
            .setPriority(Priority.LOW)
            .build()
            .getAsString(
                this
            )
    }

    fun getLink(storyId: String, chapId: String) {
        AndroidNetworking.get("https://m.truyen.tangthuvien.vn/control-ajax?story_id=$storyId&chap_id=$chapId")
            .addHeaders(
                "User-Agent",
                "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.132 Mobile Safari/537.36"
            )
            .setPriority(Priority.LOW)
            .build()
            .getAsString(object : StringRequestListener {
                override fun onResponse(response: String?) {
                    var document = Jsoup.parse(response)
                    var elements = document.select("a")
                    var preLink = elements.first().attr("href")
                    var nextLink = elements.last().attr("href")
                    callBack.setLinkChap(LinkChap(preLink,nextLink))
                }

                override fun onError(anError: ANError?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

            }
            )
    }

}