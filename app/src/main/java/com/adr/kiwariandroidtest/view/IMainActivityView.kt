package com.adr.kiwariandroidtest.view

import com.adr.kiwariandroidtest.model.UsersModel

interface IMainActivityView {
    fun chatPartner(avatar: String, name: String)
    fun onLogoutStatus(status: Boolean)
    fun onSetData(data: ArrayList<UsersModel.User>)
    fun onContactClicked(userId: String)
}