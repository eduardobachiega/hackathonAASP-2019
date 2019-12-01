package br.com.edsb.hackathon.ui.fragments.chatbot

import android.util.Log
import br.com.edsb.hackathon.R
import br.com.edsb.hackathon.data.model.ChatMessage

class ChatBotPresenter(private val view: ChatBotInterfaces.View) : ChatBotInterfaces.Presenter {
    private var model: ChatBotModel? = null

    init {
        model = ChatBotModel(this)
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