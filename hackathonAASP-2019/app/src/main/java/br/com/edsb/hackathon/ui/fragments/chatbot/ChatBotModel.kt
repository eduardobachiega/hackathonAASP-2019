package br.com.edsb.hackathon.ui.fragments.chatbot

import android.content.Context
import br.com.edsb.hackathon.data.chatbot.ChatBotManager

class ChatBotModel(private val presenter: ChatBotInterfaces.Presenter) : ChatBotInterfaces.Model {
    var chatBot: ChatBotManager? = null

    override fun initChatBot(context: Context, onSuccess: () -> Any?, onFailure: () -> Any?) {
        chatBot = ChatBotManager()
        chatBot?.initChatBot(context, onSuccess, onFailure)
    }

    override fun sendMessage(message: String, onMessageReceived: (message: String) -> Any?,
                             onErrorReceived: () -> Any?) {
        chatBot?.sendMessage(message, onMessageReceived, onErrorReceived)
    }
}