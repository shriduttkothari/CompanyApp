package com.viseo.companyapp.activity

import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.viseo.companyapp.R
import com.viseo.companyapp.adapter.MemberListAdapter
import com.viseo.companyapp.model.Member
import kotlinx.android.synthetic.main.activity_member_list.*
import java.util.*

/**
 * Member List Activity
 *
 * @author Shridutt.Kothari
 */
class MemberListActivity : AppCompatActivity() {

    private var mAdapter: MemberListAdapter?= null;
    private var mMembers: MutableList<Member> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member_list)

        mMembers = intent.getParcelableArrayListExtra(MEMBER_LIST)
        mAdapter = MemberListAdapter(this, mMembers, R.layout.member_item)

        memberRecyclerView!!.layoutManager = LinearLayoutManager(this)
        memberRecyclerView!!.adapter = mAdapter
}

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu_member; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_member, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_sort -> {
                showSortDiaog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showSortDiaog() {
        val options: Array<String> = arrayOf(getString(R.string.ascending) + getString(R.string.name), getString(R.string.descending) + getString(R.string.name),getString(R.string.ascending) + getString(R.string.age), getString(R.string.descending) + getString(R.string.age));
        val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this , R.style.MyDialogTheme)
        alertDialogBuilder.setTitle(R.string.sort_by)
        alertDialogBuilder.setItems(options, object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                when (which) {
                    0 -> {
                        //Ascending By Name
                        Collections.sort(mMembers, Member.BY_MEMBER_NAME_ASCENDING)
                        mAdapter!!.updateDataSet(mMembers)
                    }
                    1 -> {
                        //Descending By Name
                        Collections.sort(mMembers, Member.BY_MEMBER_NAME_DESSCENDING)
                        mAdapter!!.updateDataSet(mMembers)
                    }
                    2 -> {
                        //Descending By Age
                        Collections.sort(mMembers, Member.BY_MEMBER_AGE_ASCENDING)
                        mAdapter!!.updateDataSet(mMembers)
                    }
                    3 -> {
                        //Descending By Age
                        Collections.sort(mMembers, Member.BY_MEMBER_AGE_DESSCENDING)
                        mAdapter!!.updateDataSet(mMembers)
                    }
                }
            }
        })
        alertDialogBuilder.create().show()
    }

    companion object {
        private val TAG = MemberListActivity::class.java.simpleName
        public const val MEMBER_LIST = "memberList"
    }
}
