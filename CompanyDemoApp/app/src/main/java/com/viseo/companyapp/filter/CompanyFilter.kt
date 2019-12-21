package com.viseo.companyapp.filter

import android.widget.Filter
import com.viseo.companyapp.adapter.CompanyListAdapter
import com.viseo.companyapp.model.Company

/**
 * Custom filter class to filter the list of companyName based on their companyName name
 * @author Shridutt.Kothari
 */
class CompanyFilter: Filter {

    private var adapter: CompanyListAdapter
    private var filterList: List<Company>

    constructor(filterList: List<Company>,adapter: CompanyListAdapter) : super() {
        this.filterList = filterList
        this.adapter = adapter

    }

    override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
        @Suppress("UNCHECKED_CAST")
        adapter.updateDataSet(results!!.values as ArrayList<Company>)
    }

    override fun performFiltering(constraint: CharSequence?): FilterResults {
        var results: FilterResults = FilterResults()

        if(constraint!!.isNotEmpty()) {
            var upperCaseConstraint = constraint.toString().toUpperCase();
            var filteredCompany: ArrayList<Company> = ArrayList()
            filterList.forEach {
                if(it.companyName!!.toUpperCase().contains(upperCaseConstraint)) {
                    filteredCompany.add(it)
                }
            }
            results.count = filteredCompany.size
            results.values = filteredCompany
        } else {
            results.count = filterList.size
            results.values = filterList
        }
        return results
    }

}