package com.viseo.companyapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.viseo.companyapp.model.Member
import kotlinx.android.synthetic.main.member_item.view.*

/**
 * RecyclerView Adapter for Member List
 *
 * @author Shridutt.Kothari
 */
class MemberListAdapter : RecyclerView.Adapter<MemberListAdapter.MemberViewHolder> {

    private val context: Context
    private var mMemberList: List<Member>
    private val mRowLayout: Int

    fun updateDataSet(memberList: List<Member>) {
        this.mMemberList = memberList
        this.notifyDataSetChanged()
    }

    constructor(context: Context, memberList: List<Member>, rowLayout: Int): super() {
        this.context = context
        this.mMemberList = memberList
        this.mRowLayout = rowLayout
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(mRowLayout, parent, false)
        return MemberViewHolder(view)
    }

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        holder.memberName?.text =  mMemberList[position].name!!.first!!.toString() + " "+ mMemberList[position].name!!.last!!.toString()
        holder.memberAge?.text =  mMemberList[position].age!!.toString()
        holder.containerView.setOnClickListener {
            //TODO: we can create an activity to show member details and launch it from here
        }
    }

    override fun getItemCount(): Int {
        return mMemberList.size
    }

    class MemberViewHolder(val containerView: View) : RecyclerView.ViewHolder(containerView) {
        val memberName = containerView.memberName;
        val memberAge = containerView.memberAge;
    }
}
