package com.adr.kiwariandroidtest.model

import java.util.*

class TextMessageModel {

    data class TextMessage(
        val text: String = "",
        val senderId: String = "",
        val receiverId: String = "",
        val time: Long
    )
}