package com.adr.kiwariandroidtest.presenter

import com.adr.kiwariandroidtest.model.TextMessageModel

interface IChatActivityPresenter {
    fun findAvatarName(uid: String)
    fun sendMessage(textMessage: TextMessageModel.TextMessage)
    fun updateChat()
}