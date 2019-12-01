package br.com.edsb.hackathon.data.chatbot

import ai.api.AIConfiguration
import ai.api.android.AIService
import ai.api.model.AIRequest
import ai.api.model.AIResponse
import android.annotation.SuppressLint
import android.content.Context
import android.os.AsyncTask

class ChatBotManager {
    var aiService: AIService? = null

    fun initChatBot(context: Context, onSuccess: () -> Any?, onFailure: () -> Any?) {
        try {
            val config = ai.api.android.AIConfiguration("da52144fb37040059bf418309deda324",
                    AIConfiguration.SupportedLanguages.PortugueseBrazil,
                    ai.api.android.AIConfiguration.RecognitionEngine.System)

            aiService = AIService.getService(context, config)

            onSuccess()
        } catch (e: Exception) {
            onFailure()
        }
    }

    @SuppressLint("StaticFieldLeak")
    fun sendMessage(message: String, onMessageReceived: (message: String) -> Any?, onErrorReceived: () -> Any?) {
        object : AsyncTask<Void, Void, AIResponse>() {
            override fun doInBackground(vararg voids: Void): AIResponse? {
                try {
                    val aiRequest = AIRequest()
                    aiRequest.setQuery("Olá")
                    aiService?.textRequest(aiRequest)
                    return aiService?.textRequest(aiRequest)
                } catch (e: Exception) {
                    onErrorReceived()
                }

                return null

            }

            override fun onPostExecute(aiResponse: AIResponse?) {
                super.onPostExecute(aiResponse)
                if (aiResponse != null) {
                    onMessageReceived(aiResponse.result.fulfillment.speech)
                } else {
                    onErrorReceived()
                }
            }
        }.execute()
    }
}