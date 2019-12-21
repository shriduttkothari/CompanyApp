package com.viseo.companyapp.com.viseo.companyapp.rest

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Singleton Kotlin Class for Rest operation from the app
 *
 * @author Shridutt.Kothari
 */
object RestClient {

    private val BASE_URL = "https://next.json-generator.com"
    private var mRetrofit: Retrofit? = null

    val client: Retrofit
        get() {
            if (mRetrofit == null) {
                mRetrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return this.mRetrofit!!
        }
}
