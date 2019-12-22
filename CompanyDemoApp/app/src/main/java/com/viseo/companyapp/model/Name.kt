package com.viseo.companyapp.model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 *
 * @author Shridutt.Kothari
 */
class Name() : Parcelable {
    var first: String? = null
    var last: String? = null

    constructor(parcel: Parcel) : this() {
        first = parcel.readString()
        last = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(first)
        parcel.writeString(last)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<Name> {
            override fun createFromParcel(parcel: Parcel): Name {
                return Name(parcel)
            }

            override fun newArray(size: Int): Array<Name?> {
                return arrayOfNulls(size)
            }
        }
    }
}