package com.adr.kiwariandroidtest.model

class TextMessageModel {

    data class TextMessage(
        val text: String = "",
        val senderId: String = "",
        val receiverId: String = "",
        val time: Long
    )
}