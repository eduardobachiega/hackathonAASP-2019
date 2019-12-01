package br.com.edsb.hackathon.ui.activities.login

import android.app.Activity
import br.com.edsb.hackathon.data.firebase.UserFirebaseHelper
import br.com.edsb.hackathon.ui.activities.base.BaseInterfaces
import com.google.firebase.auth.PhoneAuthProvider

interface LoginInterfaces {
    interface Model {
        fun requestSms(activity: Activity, phoneNumber: String, smsCallback:
        PhoneAuthProvider.OnVerificationStateChangedCallbacks)

        fun getUser(userCallback: UserFirebaseHelper.Companion.UserSearchCallback)
        fun saveUserInfo(uid: String?, phoneNumber: String?)
    }

    interface View : BaseInterfaces.View {
        fun navigateToRegister()
        fun navigateToMenu()
    }

    interface Presenter {
        fun loginWithSms(phoneNumber: String)
        fun loginWithSmsCode(code: String)
    }
}