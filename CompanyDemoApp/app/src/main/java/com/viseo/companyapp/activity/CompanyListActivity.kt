package com.viseo.companyapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager

import com.viseo.companyapp.R
import com.viseo.companyapp.adapter.CompanyListAdapter
import com.viseo.companyapp.model.Company
import com.viseo.companyapp.rest.APIService
import com.viseo.companyapp.rest.RestClient
import kotlinx.android.synthetic.main.activity_company_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

/**
 * Company List Activity
 *
 * @author Shridutt.Kothari
 */
class CompanyListActivity : AppCompatActivity() {

    private var mApiService: APIService? = null
    private var mAdapter: CompanyListAdapter?= null;
    private var mCompanies: MutableList<Company> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company_list)

        mApiService = RestClient.client.create(APIService::class.java)

        companyRecyclerView!!.layoutManager = LinearLayoutManager(this)

        mAdapter = CompanyListAdapter(this, mCompanies, R.layout.company_item)
        companyRecyclerView!!.adapter = mAdapter

        fetchCompanyList()
    }

    private fun fetchCompanyList() {
        val call = mApiService!!.fetchCompanyList();
        call.enqueue(object : Callback<List<Company>> {

            override fun onResponse(call: Call<List<Company>>, response: Response<List<Company>>) {

                Log.d(TAG, "Total Companies: " + response.body()!!.size)
                val companyList = response.body()
                if (companyList != null) {
                    mCompanies.addAll(companyList)
                    mAdapter!!.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<List<Company>>, t: Throwable) {
                Log.e(TAG, "Got error : " + t.localizedMessage)
                Toast.makeText(applicationContext, "Got error : " + t.localizedMessage, Toast.LENGTH_LONG).show()
            }
        })
    }

    companion object {
        private val TAG = CompanyListActivity::class.java.simpleName
    }

}
