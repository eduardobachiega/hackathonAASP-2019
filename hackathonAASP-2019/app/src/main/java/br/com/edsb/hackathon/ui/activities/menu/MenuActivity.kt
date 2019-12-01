package br.com.edsb.hackathon.ui.activities.menu

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import br.com.edsb.hackathon.R
import br.com.edsb.hackathon.ui.activities.base.BaseActivity
import br.com.edsb.hackathon.ui.fragments.settings.SettingsFragment
import br.com.edsb.hackathon.ui.activities.login.LoginActivity
import br.com.edsb.hackathon.ui.activities.register.info.EditUserActivity
import br.com.edsb.hackathon.ui.activities.register.photo.RegisterPhotoActivity
import br.com.edsb.hackathon.ui.fragments.chatbot.ChatBotFragment
import br.com.edsb.hackathon.ui.fragments.initial.InitialFragment
import br.com.edsb.hackathon.ui.fragments.myprofile.MyProfileFragment
import br.com.edsb.hackathon.ui.fragments.pills.PillsFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.app_bar_menu.*
import java.lang.Exception

class MenuActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener,
        InitialFragment.OnFragmentInteractionListener,
        SettingsFragment.OnFragmentInteractionListener,
        ChatBotFragment.OnFragmentInteractionListener,
        MyProfileFragment.OnFragmentInteractionListener,
        PillsFragment.OnFragmentInteractionListener,
        MenuInterfaces.View {

    val presenter = MenuPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close)

        drawer.addDrawerListener(toggle)
        toggle.syncState()

        if (true) {
            navigationView.menu.clear()
            navigationView.inflateMenu(R.menu.menu_drawer1)
        } else {
            navigationView.menu.clear()
            navigationView.inflateMenu(R.menu.menu_drawer2)
        }

        val animationDrawable = navigationView.getHeaderView(0)
                .findViewById<LinearLayout>(R.id.navHeaderBackground)
                .background as AnimationDrawable

        animationDrawable.setEnterFadeDuration(5000)
        animationDrawable.setExitFadeDuration(2000)

        animationDrawable.start()

        presenter.getUserInfo()

        performTransaction(InitialFragment())

        navigationView.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START)
    }

    private fun fragmentTransaction(fragment: Fragment?, initial: Boolean) {
        drawer.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerStateChanged(p0: Int) {

            }

            override fun onDrawerSlide(p0: View, p1: Float) {

            }

            override fun onDrawerClosed(p0: View) {
                performTransaction(fragment)
                drawer.removeDrawerListener(this)
            }

            override fun onDrawerOpened(p0: View) {

            }
        })

        drawer.closeDrawer(GravityCompat.START)
        if (initial)
            performTransaction(fragment)
    }

    private fun performTransaction(fragment: Fragment?) {
        if (fragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container, fragment!!)
            transaction.commit()
        } else {
            Log.e("FRAGMENT", "NULL")
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        var fragment: Fragment? = null
        var fragmentClass: Class<*>? = null

        var performTransaction = true

        when (id) {
            R.id.nav_initial -> fragmentClass = InitialFragment::class.java
            R.id.nav_settings -> fragmentClass = ChatBotFragment::class.java
            R.id.nav_my_profile -> fragmentClass = MyProfileFragment::class.java
            R.id.nav_pill -> fragmentClass = PillsFragment::class.java
            R.id.nav_logout -> {
                performTransaction = false
                showConfirmDialog(getString(R.string.exit), getString(R.string.logout_confirm),
                        getString(R.string.yes),
                        getString(R.string.no),
                        {
                            presenter.registerLogout()
                        },
                        {

                        })
            }
        }

        if (performTransaction) {
            try {
                fragment = fragmentClass!!.newInstance() as Fragment
            } catch (e: Exception) {
                e.printStackTrace()
            }

            fragmentTransaction(fragment, false)
        }

        return true
    }

    override fun navigateToLogin() = startActivity(Intent(this,
            LoginActivity::class.java))

    override fun onFragmentInteraction(uri: Uri) {

    }

    override fun setMenuHeader(photoUri: Uri?, userName: String?) {
        val headerView = navigationView.getHeaderView(0)
        headerView.findViewById<TextView>(R.id.tvUserName).text = userName
        val imageView = headerView.findViewById<CircleImageView>(R.id.ivProfilePicture)

        if (photoUri != null)
            Glide.with(this)
                    .load(photoUri)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(imageView)

        imageView.setOnClickListener {
            val intent = Intent(this, RegisterPhotoActivity::class.java)
            intent.putExtra("fromMenu", true)
            proceedToActivity(intent)
        }

        headerView.findViewById<TextView>(R.id.tvEditProfile).setOnClickListener {
            val intent = Intent(this, EditUserActivity::class.java)
            intent.putExtra("fromMenu", true)
            proceedToActivity(intent)
        }
    }

    private fun proceedToActivity(intent: Intent) {
        startActivity(intent)
        finish()
    }
}
