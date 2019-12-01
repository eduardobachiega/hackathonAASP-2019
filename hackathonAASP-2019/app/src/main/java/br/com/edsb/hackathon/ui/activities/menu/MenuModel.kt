package br.com.edsb.hackathon.ui.activities.menu

import br.com.edsb.hackathon.data.firebase.UserFirebaseHelper
import br.com.edsb.hackathon.data.preferences.AppPreferences

class MenuModel(private val presenter: MenuInterfaces.Presenter) : MenuInterfaces.Model {
    override fun deleteDirectLogin(): Boolean {
        return try {
            AppPreferences.deletePreferences()

            true
        } catch (e: Exception) {
            false
        }
    }

    override fun logoutFirebaseUser(): Boolean {
        return try {
            UserFirebaseHelper.logoutUser()

            true
        } catch (e: Exception) {
            false
        }
    }

    override fun getUserName() = AppPreferences.getUserName()

    override fun getUserPhotoUrl() {
        UserFirebaseHelper.getProfilePictureDownloadURL().addOnSuccessListener {
            presenter.receivePhotoUrl(it)
        }.addOnFailureListener {
            presenter.receivePhotoUrl(null)
        }
    }
}