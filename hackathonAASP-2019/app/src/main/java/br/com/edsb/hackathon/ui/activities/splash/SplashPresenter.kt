package br.com.edsb.hackathon.ui.activities.splash

class SplashPresenter(private val view: SplashInterfaces.View) : SplashInterfaces.Presenter {

    private val model = SplashModel(this)

    override fun verifyLoggedUser() {
        //view.openMenu()
        if (model.getLoggedInfo()!!)
            view.openMenu()
        else
            view.openLogin()
    }
}