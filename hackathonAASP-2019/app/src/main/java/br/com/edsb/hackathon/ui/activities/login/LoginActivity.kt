package br.com.edsb.hackathon.ui.activities.login

import android.content.Intent
import android.os.Bundle
import br.com.edsb.hackathon.R
import br.com.edsb.hackathon.ui.activities.base.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.content_login.*
import android.graphics.drawable.AnimationDrawable
import br.com.edsb.hackathon.ui.activities.menu.MenuActivity
import br.com.edsb.hackathon.ui.activities.register.chatbot.RegisterUserActivity
import br.com.edsb.hackathon.utils.mask.Mask

class LoginActivity : BaseActivity(), LoginInterfaces.View {
    private val presenter = LoginPresenter(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etPhoneNumber.addTextChangedListener(Mask.insert(getString(R.string.phone_mask),
                etPhoneNumber))

        fab.setOnClickListener {
            presenter.loginWithSms("+55${Mask.unmask(etPhoneNumber.text.toString())}")
        }

        val animationDrawable = content_background.background as AnimationDrawable

        animationDrawable.setEnterFadeDuration(5000)
        animationDrawable.setExitFadeDuration(2000)

        animationDrawable.start()
    }

    override fun navigateToRegister() = startActivity(Intent(this,
            RegisterUserActivity::class.java))

    override fun navigateToMenu() = startActivity(Intent(this,
            MenuActivity::class.java))

    override fun onBackPressed() {

    }
}
