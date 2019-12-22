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

    private val mContext: Context
    private var mCompanyList: List<Company>
    private var mFilterList: List<Company>
    private var mCompanyFilter: CompanyFilter? = null
    private val mRowLayout: Int

    fun updateDataSet(mCompanyList: List<Company>) {
        this.mCompanyList = mCompanyList
        this.notifyDataSetChanged()
    }

    constructor(context: Context, mCompanyList: List<Company>, mRowLayout: Int): super() {
        this.mContext = context
        this.mCompanyList = mCompanyList
        this.mFilterList = mCompanyList
        this.mRowLayout = mRowLayout
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(mRowLayout, parent, false)
        return CompanyViewHolder(view)
    }

    override fun onBindViewHolder(holder: CompanyViewHolder, position: Int) {
        holder.companyName?.text =  mCompanyList[position].companyName;
        holder.companyWebsite?.text =  mCompanyList[position].website;
        holder.companyAbout?.text =  mCompanyList[position].about;

        holder.containerView.setOnClickListener {
            var members = mCompanyList[position].members
            val intent: Intent = Intent(mContext, MemberListActivity::class.java)
            intent.putExtra(MemberListActivity.COMPANY_NAME, mCompanyList[position].companyName)
            intent.putParcelableArrayListExtra(MemberListActivity.MEMBER_LIST, members as java.util.ArrayList<out Parcelable>)
            mContext.startActivity(intent);
        }
    }

    override fun getItemCount(): Int {
        return mCompanyList.size
    }

    class CompanyViewHolder(val containerView: View) : RecyclerView.ViewHolder(containerView) {
       val companyName = containerView.companyName;
        val companyWebsite = containerView.companyWebsite;
        val companyAbout = containerView.companyAbout;
    }

    override fun getFilter(): Filter {
        if(mCompanyFilter == null) {
            mCompanyFilter = CompanyFilter(mFilterList, this)
        }

        return mCompanyFilter as Filter
    }
}
