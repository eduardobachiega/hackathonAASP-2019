package br.com.edsb.hackathon.ui.activities.menu

import android.net.Uri
import br.com.edsb.hackathon.R

class MenuPresenter(private val view: MenuInterfaces.View) : MenuInterfaces.Presenter {
    private val model = MenuModel(this)

    override fun registerLogout() {
        if (model.deleteDirectLogin())
            if (model.deleteDirectLogin())
                view.navigateToLogin()
            else
                showLogoutError()
        else
            showLogoutError()
    }

    private fun showLogoutError() = view.showWarningDialog(view.getStringResource(R.string.attention),
            view.getStringResource(R.string.user_logout_failed))

    override fun getUserInfo() = model.getUserPhotoUrl()

    override fun receivePhotoUrl(uri: Uri?) = setMenuData(uri)

    private fun setMenuData(uri: Uri?) = view.setMenuHeader(uri, model.getUserName())
}