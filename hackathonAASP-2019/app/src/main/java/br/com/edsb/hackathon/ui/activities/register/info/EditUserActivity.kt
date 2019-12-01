package br.com.edsb.hackathon.ui.activities.register.info

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import br.com.edsb.hackathon.R
import br.com.edsb.hackathon.data.model.User
import br.com.edsb.hackathon.ui.activities.base.BaseActivity
import br.com.edsb.hackathon.ui.activities.menu.MenuActivity
import br.com.edsb.hackathon.ui.activities.register.photo.RegisterPhotoActivity
import kotlinx.android.synthetic.main.activity_edit_user.*
import kotlinx.android.synthetic.main.content_edit_user.*

class EditUserActivity : BaseActivity(), EditUserInterfaces.View {
    private val presenter = EditUserPresenter(this)
    private var isFromMenu = false
    private var firstTime = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_user)

        fab.setOnClickListener {
            presenter.register(firstTime)
        }

        if (intent.getBooleanExtra("fromMenu", false)) {
            isFromMenu = true
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        presenter.getUserInfo(isFromMenu)
    }

    override fun fillFields(user: User) {
        etEmail.setText(user.email)
        etName.setText(user.name)
        etLastName.setText(user.lastName)
    }

    override fun getEmail() = etEmail.text.toString()

    override fun getName() = etName.text.toString()

    override fun getLastName() = etLastName.text.toString()

    override fun proceedActivity() {
        if (isFromMenu) {
            proceedToMenu()
        } else {
            val intent = Intent(this, RegisterPhotoActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
    }

    override fun proceedToMenu() {
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
    }

    override fun onBackPressed() {
        if (isFromMenu) {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        } else {
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun setFirstTime(firstTime: Boolean) {
        this.firstTime = firstTime
    }
}
