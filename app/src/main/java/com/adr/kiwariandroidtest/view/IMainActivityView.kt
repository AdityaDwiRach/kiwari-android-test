package com.adr.kiwariandroidtest.view

interface IMainActivityView {
    fun chatPartner(avatar: String, name: String)
    fun onLogoutStatus(status: Boolean)
}