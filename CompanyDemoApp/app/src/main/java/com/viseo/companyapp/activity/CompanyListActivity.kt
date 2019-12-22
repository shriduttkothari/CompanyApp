package com.viseo.companyapp.activity

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Filter
import android.widget.Filterable
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
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
    private var loadProgress: ProgressBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company_list)
        loadProgress = findViewById(R.id.progressBar)
        mApiService = RestClient.client.create(APIService::class.java)
        companyRecyclerView!!.layoutManager = LinearLayoutManager(this)

        mAdapter = CompanyListAdapter(this, mCompanies, R.layout.company_item)
        companyRecyclerView!!.adapter = mAdapter

        fetchCompanyList()
    }

    /**
     * Fetches company data from server
     */
    private fun fetchCompanyList() {
        loadProgress!!.setVisibility(View.VISIBLE);
        val call = mApiService!!.fetchCompanyList();
        call.enqueue(object : Callback<List<Company>> {

            override fun onResponse(call: Call<List<Company>>, response: Response<List<Company>>) {
                Log.d(TAG, "Total Companies: " + response.body()!!.size)
                val companyList = response.body()
                if (companyList != null) {
                    mCompanies.addAll(companyList)
                    mAdapter!!.updateDataSet(mCompanies)
                }
                loadProgress!!.setVisibility(View.GONE);
            }

            override fun onFailure(call: Call<List<Company>>, t: Throwable) {
                Log.e(TAG, "Got error : " + t.localizedMessage)
                loadProgress!!.setVisibility(View.GONE);
                showSnackBar(getString(R.string.unable_to_fetch_data), companyRelativeLayout)
            }
        })
    }

    fun showSnackBar(text: String, parentLayout: View) {
        val snackBar = Snackbar
            .make(parentLayout, text, Snackbar.LENGTH_INDEFINITE)
            .setAction(getString(R.string.retry), object : View.OnClickListener {
                override fun onClick(v: View?) {
                    fetchCompanyList()
                }
            })
        snackBar.show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu_company; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_company, menu)
        var searchMenuItem = menu.findItem(R.id.action_search)
        val searchView = searchMenuItem!!.getActionView() as androidx.appcompat.widget.SearchView
        searchView.setOnQueryTextListener(object :  androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                //Will be called when user types something
                var mFilterableAdapter: Filterable = mAdapter as Filterable
                var filter: Filter = mFilterableAdapter.getFilter()
                filter.filter(newText)
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                //called when user finally submits the search query
                var mFilterableAdapter: Filterable = mAdapter as Filterable
                var filter: Filter = mFilterableAdapter.getFilter()
                filter.filter(query)
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                true
            }
            R.id.action_sort -> {
                showSortDiaog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showSortDiaog() {
        val options: Array<String> = arrayOf(getString(R.string.ascending) + getString(R.string.name), getString(R.string.descending) + getString(R.string.name));
        val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this , R.style.MyDialogTheme)
        alertDialogBuilder.setTitle(R.string.sort_by)
        alertDialogBuilder.setItems(options, object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                    if(which == 0) {
                        //Ascending
                        Collections.sort(mCompanies, Company.BY_COMPANY_NAME_ASCENDING)
                        mAdapter!!.updateDataSet(mCompanies)
                    } else if(which == 1) {
                        //Descending
                        Collections.sort(mCompanies, Company.BY_COMPANY_NAME_DESSCENDING)
                        mAdapter!!.updateDataSet(mCompanies)
                    }
            }
        })
        alertDialogBuilder.create().show()
    }

    companion object {
        private val TAG = CompanyListActivity::class.java.simpleName
    }

}
