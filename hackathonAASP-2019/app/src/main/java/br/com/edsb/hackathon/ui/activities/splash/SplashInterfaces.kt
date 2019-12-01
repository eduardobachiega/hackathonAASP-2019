package br.com.edsb.hackathon.ui.activities.splash

import br.com.edsb.hackathon.ui.activities.base.BaseInterfaces

interface SplashInterfaces {
    interface Model{
        fun getLoggedInfo(): Boolean?
    }

    interface View: BaseInterfaces.View{
        fun openLogin()
        fun openMenu()
    }

    interface Presenter{
        fun verifyLoggedUser()
    }
}