package br.com.edsb.hackathon.ui.activities.register.chatbot

import android.util.Log
import br.com.edsb.hackathon.R
import br.com.edsb.hackathon.data.model.ChatMessage
import br.com.edsb.hackathon.data.model.User

class RegisterUserPresenter(private val view: RegisterUserInterfaces.View) : RegisterUserInterfaces.Presenter {
    private var model: RegisterUserModel? = null
    private val user = User()

    init {
        model = RegisterUserModel(this)
        model?.initChatBot(view.getContext(),
                {
                    Log.e("INIT", "SUCCESS INIT")
                },
                {
                    Log.e("INIT", "ERROR INIT")
                })
    }

    override fun sendMessage(message: String) {
        model?.sendMessage(message,
                {
                    val chatMessage = ChatMessage(it, "Bot", R.drawable.ruy, true)
                    view.registerMessage(chatMessage)
                },
                {
                    Log.e("MESSAGE", "ERROR RECEIVED")
                })
    }
}