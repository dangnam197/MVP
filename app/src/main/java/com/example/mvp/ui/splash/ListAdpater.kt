package com.example.mvp.ui.splash

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mvp.R
import com.example.mvp.data.db.model.Book

class ListAdpater (var listItem:ArrayList<Book>,var callback:ListAdpater.CallBack): RecyclerView.Adapter<ListAdpater.ViewHolder>(){
    interface CallBack{
        fun onClick(book: Book)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        listItem[position].name?.let { holder.bin(it) }
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var tv : TextView = itemView.findViewById(R.id.tv_item)
        init {
            itemView.setOnClickListener { callback.onClick(listItem[adapterPosition]) }
        }
        fun bin(text:String){
            tv.text = text
        }
    }
}