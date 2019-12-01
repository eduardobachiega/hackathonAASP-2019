package br.com.edsb.hackathon.ui.activities.login

import android.text.InputType
import android.util.Log
import br.com.edsb.hackathon.R
import br.com.edsb.hackathon.data.firebase.UserFirebaseHelper
import br.com.edsb.hackathon.data.model.User
import br.com.edsb.hackathon.data.preferences.AppPreferences
import com.afollestad.materialdialogs.WhichButton
import com.afollestad.materialdialogs.actions.setActionButtonEnabled
import com.afollestad.materialdialogs.input.getInputField
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.FirebaseAuth

class LoginPresenter(private val view: LoginInterfaces.View) : LoginInterfaces.Presenter {
    private val model = LoginModel(this)
    private val mAuth = FirebaseAuth.getInstance()
    private var mVerificationId: String? = null

    override fun loginWithSms(phoneNumber: String) {
        view.showProgressDialog()
        view.showManualCodeButton()
        view.setManualCodeClickListener {
            view.dismissProgressDialog()
            showManualCodeInput()
        }
        view.setProgressDialogText(view.getStringResource(R.string.waiting_sms))
        model.requestSms(view.getActivity(), phoneNumber, smsCallback)
    }

    private val smsCallback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
            signInWithPhoneAuthCredential(phoneAuthCredential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            Log.e("LOGIN ERROR", e.toString())
            view.dismissProgressDialog()
            view.showWarningDialog(view.getStringResource(R.string.attention),
                    view.getStringResource(R.string.sms_request_failed))
        }

        override fun onCodeAutoRetrievalTimeOut(p0: String?) {
            Log.e("LOGIN ERROR", "TIMEOUT")
            view.dismissProgressDialog()
            view.showWarningDialog(view.getStringResource(R.string.attention),
                    view.getStringResource(R.string.sms_request_failed))
            showManualCodeInput()
        }

        override fun onCodeSent(verificationId: String?, p1: PhoneAuthProvider.ForceResendingToken?) {
            mVerificationId = verificationId
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                model.saveUserInfo(it.result?.user?.uid, it.result?.user?.phoneNumber)
                Log.e("LOGIN", "SUCCESSFUL")
                view.dismissProgressDialog()
                isFirstTime()
            } else {
                Log.e("LOGIN ERROR", it.exception.toString())
                view.dismissProgressDialog()
                view.showWarningDialog(view.getStringResource(R.string.attention),
                        view.getStringResource(R.string.sms_verification_failed))
            }
        }
    }

    override fun loginWithSmsCode(code: String) {
        if (!mVerificationId.isNullOrBlank()) {
            val credential = PhoneAuthProvider.getCredential(mVerificationId!!, code)
            signInWithPhoneAuthCredential(credential)
        }
    }

    private fun showManualCodeInput() {
        view.showInputDialog(view.getStringResource(R.string.type_code),
                view.getStringResource(R.string.type_code_instruction),
                view.getStringResource(R.string.code_mask),
                InputType.TYPE_CLASS_NUMBER, 6,
                view.getStringResource(R.string.ok),
                view.getStringResource(R.string.cancel),
                {
                    val code = it.getInputField()?.text.toString()
                    loginWithSmsCode(code)
                },
                {

                },
                { dialog, codeInput ->
                    val input = dialog.getInputField()
                    var isValid = true
                    if (codeInput.length != 6) {
                        input?.error = view.getStringResource(R.string.invalid_code)
                        isValid = false
                    }

                    dialog.setActionButtonEnabled(WhichButton.POSITIVE, isValid)
                })
    }

    private fun isFirstTime() {
        model.getUser(object : UserFirebaseHelper.Companion.UserSearchCallback {
            override fun onUserFound(user: User) {
                AppPreferences.registerUserLogin()
                view.navigateToMenu()
            }

            override fun onUserNotFound() {
                view.navigateToRegister()
            }

        })
    }
}