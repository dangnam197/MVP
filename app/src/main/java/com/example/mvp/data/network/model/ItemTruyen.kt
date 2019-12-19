package com.example.truyen.model

import android.os.Parcel
import android.os.Parcelable

data class ItemTruyen(var title: String?, var link: String?):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    )
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(link)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ItemTruyen> {
        override fun createFromParcel(parcel: Parcel): ItemTruyen {
            return ItemTruyen(parcel)
        }

        override fun newArray(size: Int): Array<ItemTruyen?> {
            return arrayOfNulls(size)
        }
    }
}