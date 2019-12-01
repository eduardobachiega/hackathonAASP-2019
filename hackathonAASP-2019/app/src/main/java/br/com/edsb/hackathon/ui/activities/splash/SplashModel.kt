package br.com.edsb.hackathon.ui.activities.splash

import br.com.edsb.hackathon.data.preferences.AppPreferences

class SplashModel(private val presenter: SplashInterfaces.Presenter) : SplashInterfaces.Model {
    override fun getLoggedInfo() = AppPreferences.isUserLogged()
}