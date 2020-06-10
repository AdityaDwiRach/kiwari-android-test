package com.adr.kiwariandroidtest.view

import com.adr.kiwariandroidtest.model.TextMessageModel

interface IChatActivityView {
    fun setAvatarName(avatar: String, name: String)
    fun onSuccessSendMessage()
    fun onFailureSendMessage()
    fun onChatUpdatedSuccess(listData: ArrayList<TextMessageModel.TextMessage>)
    fun onChatUpdatedFailure()
}