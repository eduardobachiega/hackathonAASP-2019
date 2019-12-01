package br.com.edsb.hackathon.ui.activities.register.chatbot

import android.content.Context
import br.com.edsb.hackathon.data.model.ChatMessage
import br.com.edsb.hackathon.data.model.User
import br.com.edsb.hackathon.ui.activities.base.BaseInterfaces

interface RegisterUserInterfaces {
    interface Model{
        fun registerUserData(newUser: User): Boolean
        fun registerPreferences(name: String, lastName: String)
        fun initChatBot(context: Context, onSuccess: () -> Any?, onFailure: () -> Any?)
        fun sendMessage(message: String, onMessageReceived: (message: String) -> Any?, onErrorReceived: () -> Any?)
    }

    interface View: BaseInterfaces.View{
        fun proceedToMenu()
        fun proceedToPhoto()
        fun registerMessage(chatMessage: ChatMessage)
    }

    interface Presenter{
        fun sendMessage(message: String)
    }
}