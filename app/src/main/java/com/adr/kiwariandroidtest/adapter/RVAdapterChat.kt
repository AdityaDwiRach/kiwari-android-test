package com.adr.kiwariandroidtest.adapter

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import com.adr.kiwariandroidtest.R
import com.adr.kiwariandroidtest.model.TextMessageModel
import kotlinx.android.synthetic.main.item_rv_chat.view.*

class RVAdapterChat : RecyclerView.Adapter<RVAdapterChat.ViewHolder>(), IRVAdapterChatModel,
    IRVAdapterChatView {

    private var dataList: ArrayList<TextMessageModel.TextMessage> = ArrayList()
    private var currentUid = ""
//    private val recyclerView by lazy { view. }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val chatText = itemView.tv_very_chat
        val relativeLayout = itemView.rl_chat
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_rv_chat, parent, false)
        )
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listData = getListData()
        holder.chatText.text = listData[position].text
        //TODO define the user id and set the layout gravity, current = end, another = start
        if (listData[position].senderId == currentUid){
            holder.relativeLayout.apply {
                setBackgroundResource(R.drawable.bg_chat_current)
                this.layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 800005)
            }
//            holder.relativeLayout.setBackgroundResource(R.drawable.bg_chat_current)
        } else {
//            holder.relativeLayout.gravity = Gravity.START
//            holder.relativeLayout.setBackgroundResource(R.drawable.bg_chat_another)
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