package com.adr.kiwariandroidtest.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.adr.kiwariandroidtest.R
import com.adr.kiwariandroidtest.model.UsersModel
import com.adr.kiwariandroidtest.view.IMainActivityView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.item_rv_main.view.*

class RVAdapterMain(private val iMainActivityView: IMainActivityView) : RecyclerView.Adapter<RVAdapterMain.ViewHolder>(), IRVAdapterMainView,
    IRVAdapterMainModel {

    private var listData: ArrayList<UsersModel.User> = ArrayList()
    private var context: Context? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val avatar: CircleImageView = itemView.civ_main
        val name: TextView = itemView.tv_contact_main
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_rv_main, parent, false)
        )
    }

    override fun getItemCount(): Int = listData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bindListData = getListData()
        Log.d("Testing", bindListData.toString())

        holder.name.text = bindListData[position].name

        if (context != null) {
            Glide.with(context!!).load(bindListData[position].avatar).into(holder.avatar)
        }

        holder.itemView.setOnClickListener {
            iMainActivityView.onContactClicked(bindListData[position].uid)
        }
    }

    override fun refreshData() {
        notifyDataSetChanged()
    }

    override fun setListData(listData: ArrayList<UsersModel.User>) {
        this.listData = listData
    }

    override fun getListData(): ArrayList<UsersModel.User> {
        return listData
    }
}