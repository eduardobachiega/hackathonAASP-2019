package br.com.edsb.hackathon.data.model

data class ChatMessage(val message: String, val username: String?, val image: Int, val isBot: Boolean, val options: List<String>?) {
    constructor(message: String, username: String?, image: Int, isBot: Boolean) : this(message, username, image, isBot, null)
}