package br.com.edsb.hackathon.ui.activities.register.chatbot

import android.content.Context
import br.com.edsb.hackathon.BuildConfig
import br.com.edsb.hackathon.data.chatbot.ChatBotManager
import br.com.edsb.hackathon.data.firebase.UserFirebaseHelper
import br.com.edsb.hackathon.data.model.User
import br.com.edsb.hackathon.data.preferences.AppPreferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.lang.Exception

class RegisterUserModel(private val presenter: RegisterUserInterfaces.Presenter) : RegisterUserInterfaces.Model {
    private val firebaseDatabase = FirebaseDatabase.getInstance()
    private val databaseReference = firebaseDatabase.reference.child(BuildConfig.FIREBASE_DATABASE)
    var chatBot: ChatBotManager? = null

    override fun registerUserData(newUser: User): Boolean =
            try {
                val user = FirebaseAuth.getInstance().currentUser
                val userReference = databaseReference.child("users")
                        .child(UserFirebaseHelper.getId())

                userReference.child("email").setValue(newUser.email)
                userReference.child("name").setValue(newUser.name)
                userReference.child("lastName").setValue(newUser.lastName)
                userReference.child("phone").setValue(user?.phoneNumber)

                registerPreferences(newUser.name!!, newUser.lastName!!)

                true
            } catch (e: Exception) {
                false
            }

    override fun registerPreferences(name: String, lastName: String) {
        AppPreferences.registerUserLogin()
        AppPreferences.registerUserName("$name $lastName")
    }

    override fun initChatBot(context: Context, onSuccess: () -> Any?, onFailure: () -> Any?) {
        chatBot = ChatBotManager()
        chatBot?.initChatBot(context, onSuccess, onFailure)
    }

    override fun sendMessage(message: String, onMessageReceived: (message: String) -> Any?,
                             onErrorReceived: () -> Any?) {
        chatBot?.sendMessage(message, onMessageReceived, onErrorReceived)
    }
}