package com.viseo.companyapp.adapter

import android.content.Context
import android.content.Intent
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.viseo.companyapp.R
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
        var imageUrl = mCompanyList[position].logo
        holder.updateWithUrl(imageUrl!!)
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
        val companyName: TextView = containerView.companyName;
        val companyWebsite: TextView = containerView.companyWebsite;
        val companyAbout: TextView = containerView.companyAbout;
        private val companyIcon: ImageView = containerView.companyIcon

        fun updateWithUrl(url: String) {
            Picasso.get()
                .load(url!!.replace("http://", "https://"))
                .into(companyIcon)
        }
    }

    override fun getFilter(): Filter {
        if(mCompanyFilter == null) {
            mCompanyFilter = CompanyFilter(mFilterList, this)
        }

        return mCompanyFilter as Filter
    }
}
