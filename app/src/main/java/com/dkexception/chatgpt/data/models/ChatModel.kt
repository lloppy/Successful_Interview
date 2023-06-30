package com.dkexception.chatgpt.data.models

data class ChatModel(

    val textOrUrl: String,

    val userType: ChatUserType,

    val chatType: ChatType = ChatType.TEXT

) {

    companion object {

        fun getChatStart() = mutableListOf(
            ChatModel("Hello, how may I help you today?", ChatUserType.AI)
        )

        fun getDummyChatListForUIPreview() = listOf(
            ChatModel("Hello, how may I help you today?", ChatUserType.AI),
            ChatModel("Hi.. wanted to discuss something", ChatUserType.HUMAN)
        )
    }
}

enum class ChatType {

    TEXT,

    IMAGE
}

enum class ChatUserType {

    AI,

    HUMAN
}

class Response {
    var query: String =
        "\"переведи предложение на русский и напиши на него ответ по китайский с переводом на русский и пиньинь: \""

}
