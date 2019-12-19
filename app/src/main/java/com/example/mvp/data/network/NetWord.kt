package com.example.mvp.data.network

import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.StringRequestListener
import com.example.truyen.model.ItemTruyen
import com.example.truyen.model.Truyen
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element


class NetWord (var callBack: CallBack){

    fun getContent(link:String){
        AndroidNetworking.get(link).setPriority(Priority.HIGH)
            .build().getAsString(object :StringRequestListener{
                override fun onResponse(response: String?) {
                    if (response != null) {
                        getJsoupContent(response)
                    }
                }

                override fun onError(anError: ANError?) {

                }
            })
    }
    fun getJsoupContent(text:String){
        val document : Document? = Jsoup.parse(text)
        val element = document?.getElementById("bookContentBody")
        val elementTile = document?.getElementsByClass("truncate")!!.last()
        val elementNext =  document?.getElementById("btnNextChapter")
        val elementPre = document?.getElementById("btnPreChapter")
        var txt = element?.wholeText()
        var linkNext =elementNext?.attr("href")
        var linkPre = elementPre?.attr("href")
        if(txt!=null) {
            var content = "${elementTile.ownText()} \n\n${txt?.replace("<br>", "\n\t\t")}"

            var truyen = Truyen(content,"https://wikidich.com$linkPre","https://wikidich.com$linkNext",elementTile.wholeText())
            callBack.setText(truyen)
            //Log.d("ketqua",text)
        }



    }
    fun getListItem(link:String){
        AndroidNetworking.get(link).setPriority(Priority.HIGH)
            .build().getAsString(object :StringRequestListener{
                override fun onResponse(response: String?) {
                    if (response != null) {
                        getJsoupListItem(response)
                    }
                }

                override fun onError(anError: ANError?) {

                }
            })
    }
    fun getJsoupListItem(text:String){
        val document : Document? = Jsoup.parse(text)
        val elements = document?.select("a.truncate")
        val listItem = ArrayList<ItemTruyen>()
        if (elements != null) {
            for(element:Element in elements.iterator()) {
                listItem.add(ItemTruyen(element.ownText(), element.attr("href")))
                Log.d("ketqua",element.wholeText())
            }
        }
        callBack.setListItemTruyen(listItem)

    }
    interface CallBack{
        fun setText(truyen: Truyen)
        fun setListItemTruyen(listItemTruyen:ArrayList<ItemTruyen>)
    }
}