package com.example.myapplication1

import android.os.Parcel
import android.os.Parcelable

data class callModel(
    val callName :String,
    val time:String,
    val image :Int,
    val callImage :Int
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(p0: Parcel, p1: Int) {
        p0.writeString(callName)
        p0.writeString(time)
        p0.writeInt(image)
    }

    companion object CREATOR : Parcelable.Creator<callModel> {
        override fun createFromParcel(parcel: Parcel): callModel {
            return callModel(parcel)
        }

        override fun newArray(size: Int): Array<callModel?> {
            return arrayOfNulls(size)
        }
    }
}
