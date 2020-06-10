package com.adr.kiwariandroidtest.adapter

import com.adr.kiwariandroidtest.model.UsersModel

interface IRVAdapterMainModel {
    fun setListData(listData: ArrayList<UsersModel.User>)
    fun getListData(): ArrayList<UsersModel.User>
}