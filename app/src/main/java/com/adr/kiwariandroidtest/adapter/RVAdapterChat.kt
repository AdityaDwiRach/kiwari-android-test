package com.adr.kiwariandroidtest.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.adr.kiwariandroidtest.R
import com.adr.kiwariandroidtest.model.TextMessageModel
import kotlinx.android.synthetic.main.item_rv_chat.view.*
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class RVAdapterChat : RecyclerView.Adapter<RVAdapterChat.ViewHolder>(), IRVAdapterChatModel,
    IRVAdapterChatView {

    private var dataList: ArrayList<TextMessageModel.TextMessage> = ArrayList()
    private var currentUid = ""

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val chatText: TextView = itemView.tv_very_chat
        val chatDate: TextView = itemView.tv_very_date_chat
        val relativeLayout: RelativeLayout = itemView.rl_chat
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_rv_chat, parent, false)
        )
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listData = getListData()
        val date = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault()).format(Date(listData[position].time))

        holder.chatText.text = listData[position].text
        holder.chatDate.text = date

        if (listData[position].senderId == currentUid){
            holder.relativeLayout.apply {
                setBackgroundResource(R.drawable.bg_chat_current)
                this.layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 800005)
            }
        } else {
            holder.relativeLayout.apply {
                setBackgroundResource(R.drawable.bg_chat_another)
                this.layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 800003)
            }
        }
    }

    override fun setListData(listData: ArrayList<TextMessageModel.TextMessage>) {
        this.dataList = listData
    }

    override fun getListData(): ArrayList<TextMessageModel.TextMessage> {
        return this.dataList
    }

    override fun setCurrentFirebaseUser(uid: String) {
        this.currentUid = uid
    }

    override fun refreshData() {
        notifyDataSetChanged()
    }
}