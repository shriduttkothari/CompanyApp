package com.viseo.companyapp.rest

import com.viseo.companyapp.model.Company
import retrofit2.Call
import retrofit2.http.GET

/**
 * API Interface to configure API endpoints with method
 *
 * @author shridutt.kothari
 */
interface APIService {

    @GET("/api/json/get/Vk-LhK44U")
    fun fetchCompanyList(): Call<List<Company>>
}
