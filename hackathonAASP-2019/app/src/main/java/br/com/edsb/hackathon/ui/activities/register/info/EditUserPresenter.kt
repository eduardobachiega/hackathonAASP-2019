package br.com.edsb.hackathon.ui.activities.register.info

import br.com.edsb.hackathon.R
import br.com.edsb.hackathon.data.firebase.UserFirebaseHelper
import br.com.edsb.hackathon.data.model.User

class EditUserPresenter(private val view: EditUserInterfaces.View) : EditUserInterfaces.Presenter {
    private val model = EditUserModel(this)

    override fun register(firstTime: Boolean) {
        if (model.registerUserData(view.getEmail(), view.getName(), view.getLastName(), firstTime))
            view.proceedActivity()
        else
            view.showWarningDialog(view.getStringResource(R.string.attention),
                    view.getStringResource(R.string.user_register_failed))
    }

    override fun getUserInfo(isFromMenu: Boolean) {
        view.showProgressDialog()
        model.getUser(object : UserFirebaseHelper.Companion.UserSearchCallback {
            override fun onUserFound(user: User) {
                view.dismissProgressDialog()
                view.setFirstTime(false)
                if (!isFromMenu) {
                    model.registerPreferences(user.name!!, user.lastName!!)
                    view.proceedToMenu()
                } else {
                    view.fillFields(user)
                }
            }

            override fun onUserNotFound() {
                view.setFirstTime(true)
                view.dismissProgressDialog()
            }

        })
    }
}