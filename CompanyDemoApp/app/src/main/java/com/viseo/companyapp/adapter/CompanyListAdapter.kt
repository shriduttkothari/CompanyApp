package com.viseo.companyapp.adapter

import android.content.Context
import android.content.Intent
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.viseo.companyapp.activity.MemberListActivity
import com.viseo.companyapp.filter.CompanyFilter
import com.viseo.companyapp.model.Company
import kotlinx.android.synthetic.main.company_item.view.*

/**
 * RecyclerView Adapter for Company List
 *
 * @author Shridutt.Kothari
 */
class CompanyListAdapter : RecyclerView.Adapter<CompanyListAdapter.CompanyViewHolder>, Filterable {

    private val context: Context
    private var mCompanyList: List<Company>
    private var mfilterList: List<Company>
    private var mCompanyFilter: CompanyFilter? = null
    private val mRowLayout: Int

    fun updateDataSet(mCompanyList: List<Company>) {
        this.mCompanyList = mCompanyList
        this.notifyDataSetChanged()
    }

    constructor(context: Context, mCompanyList: List<Company>, mRowLayout: Int): super() {
        this.context = context
        this.mCompanyList = mCompanyList
        this.mfilterList = mCompanyList
        this.mRowLayout = mRowLayout
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(mRowLayout, parent, false)
        return CompanyViewHolder(view)
    }

    override fun onBindViewHolder(holder: CompanyViewHolder, position: Int) {
        holder.companyName?.text =  mCompanyList[position].companyName;
        holder.containerView.setOnClickListener {
            var members = mCompanyList[position].members
            val intent: Intent = Intent(context, MemberListActivity::class.java)
            intent.putParcelableArrayListExtra(MemberListActivity.MEMBER_LIST, members as java.util.ArrayList<out Parcelable>)
            context.startActivity(intent);
        }
    }

    override fun getItemCount(): Int {
        return mCompanyList.size
    }

    class CompanyViewHolder(val containerView: View) : RecyclerView.ViewHolder(containerView) {
       val companyName = containerView.companyName;
    }

    override fun getFilter(): Filter {
        if(mCompanyFilter == null) {
            mCompanyFilter = CompanyFilter(mfilterList, this)
        }

        return mCompanyFilter as Filter
    }
}
