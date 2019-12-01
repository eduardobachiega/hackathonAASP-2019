package br.com.edsb.hackathon.ui.activities.menu

import android.net.Uri
import br.com.edsb.hackathon.ui.activities.base.BaseInterfaces

interface MenuInterfaces {
    interface Model{
        fun logoutFirebaseUser(): Boolean
        fun deleteDirectLogin(): Boolean
        fun getUserName(): String?
        fun getUserPhotoUrl()
    }

    interface View: BaseInterfaces.View{
        fun navigateToLogin()
        fun setMenuHeader(photoUri: Uri?, userName: String?)
    }

    interface Presenter{
        fun registerLogout()
        fun getUserInfo()
        fun receivePhotoUrl(uri: Uri?)
    }
}