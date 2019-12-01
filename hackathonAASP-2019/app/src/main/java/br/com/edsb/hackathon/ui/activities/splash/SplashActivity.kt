package br.com.edsb.hackathon.ui.activities.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import br.com.edsb.hackathon.R
import br.com.edsb.hackathon.ui.activities.base.BaseActivity
import br.com.edsb.hackathon.ui.activities.login.LoginActivity
import br.com.edsb.hackathon.ui.activities.menu.MenuActivity

class SplashActivity : BaseActivity(), SplashInterfaces.View {
    private val presenter = SplashPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            presenter.verifyLoggedUser()
        }, 3000)
    }

    override fun openLogin() = startActivity(LoginActivity::class.java)


    override fun openMenu() = startActivity(MenuActivity::class.java)


    private fun startActivity(targetClass: Class<*>) = startActivity(Intent(this,
            targetClass))
}
