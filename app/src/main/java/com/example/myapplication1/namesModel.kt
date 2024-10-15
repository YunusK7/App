package com.example.myapplication1

import android.os.Parcel
import android.os.Parcelable

data class namesModel(
    val statusName :String,
    val status:String,
    val image :Int,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
    ) {
    }
    override fun writeToParcel(p0: Parcel, p1: Int) {
        p0.writeString(statusName)
        p0.writeString(status)
        p0.writeInt(image)
    }
    override fun describeContents(): Int {
        return 0
    }



    companion object CREATOR : Parcelable.Creator<namesModel> {
        override fun createFromParcel(parcel: Parcel): namesModel {
            return namesModel(parcel)
        }

        override fun newArray(size: Int): Array<namesModel?> {
            return arrayOfNulls(size)
        }
    }
}
