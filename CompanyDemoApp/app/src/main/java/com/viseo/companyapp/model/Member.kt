package com.viseo.companyapp.model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 *
 * @author Shridutt.Kothari
 */
//@Parcelize
class Member() : Parcelable {

    var age: Int? = null
    var name: Name? = null
    var email: String? = null
    var phone: String? = null

    constructor(parcel: Parcel) : this() {
        age = parcel.readValue(Int::class.java.classLoader) as? Int
        name = parcel.readParcelable(Name::class.java.classLoader)
        email = parcel.readString()
        phone = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(age)
        parcel.writeParcelable(name, flags)
        parcel.writeString(email)
        parcel.writeString(phone)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<Member>
        {
            override fun createFromParcel(parcel: Parcel): Member {
                return Member(parcel)
            }

            override fun newArray(size: Int): Array<Member?> {
                return arrayOfNulls(size)
            }
        }
        var BY_MEMBER_NAME_ASCENDING = object: Comparator<Member> {
            override fun compare(object1: Member?, object2: Member?): Int {
                return object1!!.name!!.first!!.compareTo(object2!!.name!!.first!!)
            }
        }

        var BY_MEMBER_NAME_DESSCENDING = object: Comparator<Member> {
            override fun compare(object1: Member?, object2: Member?): Int {
                return object2!!.name!!.first!!.compareTo(object1!!.name!!.first!!)
            }
        }

        var BY_MEMBER_AGE_ASCENDING = object: Comparator<Member> {
            override fun compare(object1: Member?, object2: Member?): Int {
                return object1!!.age!!.compareTo(object2!!.age!!)
            }
        }

        var BY_MEMBER_AGE_DESSCENDING = object: Comparator<Member> {
            override fun compare(object1: Member?, object2: Member?): Int {
                return object2!!.age!!.compareTo(object1!!.age!!)
            }
        }
    }



}