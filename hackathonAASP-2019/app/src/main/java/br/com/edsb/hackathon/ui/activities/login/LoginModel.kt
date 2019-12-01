package br.com.edsb.hackathon.ui.activities.login

import android.app.Activity
import br.com.edsb.hackathon.BuildConfig
import br.com.edsb.hackathon.data.firebase.UserFirebaseHelper
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit
import com.google.firebase.database.FirebaseDatabase


class LoginModel(private val presenter: LoginInterfaces.Presenter) : LoginInterfaces.Model {
    private val database = FirebaseDatabase.getInstance()
    private val reference = database.reference.child(BuildConfig.FIREBASE_DATABASE)

    override fun requestSms(activity: Activity, phoneNumber: String, smsCallback:
    PhoneAuthProvider.OnVerificationStateChangedCallbacks) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                activity,
                smsCallback)
    }

    override fun saveUserInfo(uid: String?, phoneNumber: String?) {
        reference
                .child("users")
                .child(UserFirebaseHelper.getId())
                .child("uid")
                .setValue(uid!!)
    }

    override fun getUser(userCallback: UserFirebaseHelper.Companion.UserSearchCallback) =
            UserFirebaseHelper.getUser(userCallback)
}