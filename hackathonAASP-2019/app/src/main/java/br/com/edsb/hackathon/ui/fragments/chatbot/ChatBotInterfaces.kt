package br.com.edsb.hackathon.ui.fragments.chatbot

import android.content.Context
import br.com.edsb.hackathon.data.model.ChatMessage
import br.com.edsb.hackathon.ui.activities.base.BaseFragmentInterfaces

interface ChatBotInterfaces {
    interface Model {
        fun initChatBot(context: Context, onSuccess: () -> Any?, onFailure: () -> Any?)
        fun sendMessage(message: String, onMessageReceived: (message: String) -> Any?, onErrorReceived: () -> Any?)
    }

    interface View : BaseFragmentInterfaces.View {
        fun registerMessage(chatMessage: ChatMessage)
    }

    interface Presenter {
        fun sendMessage(message: String)
    }
}