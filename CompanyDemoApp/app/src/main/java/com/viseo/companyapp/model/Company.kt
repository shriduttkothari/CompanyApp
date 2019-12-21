package com.viseo.companyapp.model

import com.google.gson.annotations.SerializedName

/**
 *
 * @author Shridutt.Kothari
 */
class Company {

    @SerializedName("company")
    val companyName: String? = null
    val members: List<Member>? = null

    companion object {
        var BY_COMPANY_NAME_ASCENDING = object: Comparator<Company> {
            override fun compare(object1: Company?, object2: Company?): Int {
                return object1!!.companyName!!.compareTo(object2!!.companyName!!)
            }
        }

        var BY_COMPANY_NAME_DESSCENDING = object: Comparator<Company> {
            override fun compare(object1: Company?, object2: Company?): Int {
                return object2!!.companyName!!.compareTo(object1!!.companyName!!)
            }
        }
    }
}