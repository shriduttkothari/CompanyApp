package com.viseo.companyapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.viseo.companyapp.model.Company
import kotlinx.android.synthetic.main.copany_item.view.*

/**
 * RecyclerView Adapter for Company List
 *
 * @author Shridutt.Kothari
 */
class CompanyListAdapter(private val context: Context, private val mCompanyList: List<Company>, private val mRowLayout: Int) : RecyclerView.Adapter<CompanyListAdapter.CompanyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(mRowLayout, parent, false)
        return CompanyViewHolder(view)
    }

    override fun onBindViewHolder(holder: CompanyViewHolder, position: Int) {
        holder.companyName?.text =  mCompanyList[position].company;
        holder.containerView.setOnClickListener {
            var members = mCompanyList[position].members
            //TODO: To Launch MemberListActivity with members data
        }
    }

    override fun getItemCount(): Int {
        return mCompanyList.size
    }

    class CompanyViewHolder(val containerView: View) : RecyclerView.ViewHolder(containerView) {
       val companyName = containerView.companyName;
    }
}
