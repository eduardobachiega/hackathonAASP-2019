package br.com.edsb.hackathon.ui.activities.register.info

import br.com.edsb.hackathon.data.firebase.UserFirebaseHelper
import br.com.edsb.hackathon.data.model.User
import br.com.edsb.hackathon.ui.activities.base.BaseInterfaces

interface EditUserInterfaces {
    interface Model{
        fun registerUserData(email: String, name: String, lastName: String, firstTime: Boolean): Boolean
        fun getUser(userCallback: UserFirebaseHelper.Companion.UserSearchCallback)
        fun registerPreferences(name: String, lastName: String)
    }

    interface View: BaseInterfaces.View{
        fun getEmail(): String
        fun getName(): String
        fun getLastName(): String
        fun proceedActivity()
        fun proceedToMenu()
        fun fillFields(user: User)
        fun setFirstTime(firstTime: Boolean)
    }

    interface Presenter{
        fun register(firstTime: Boolean)
        fun getUserInfo(isFromMenu: Boolean)
    }
}