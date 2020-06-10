package com.adr.kiwariandroidtest.adapter

import com.adr.kiwariandroidtest.model.TextMessageModel
import com.adr.kiwariandroidtest.model.UsersModel

interface IRVAdapterChatModel {
    fun setListData(listData: ArrayList<TextMessageModel.TextMessage>)
    fun getListData(): ArrayList<TextMessageModel.TextMessage>
    fun setCurrentFirebaseUser(uid: String)
}