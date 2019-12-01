package br.com.edsb.hackathon.ui.activities.register.chatbot

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.MenuItem
import br.com.edsb.hackathon.R
import br.com.edsb.hackathon.data.model.ChatMessage
import br.com.edsb.hackathon.ui.activities.base.BaseActivity
import br.com.edsb.hackathon.ui.activities.menu.MenuActivity
import br.com.edsb.hackathon.ui.activities.register.photo.RegisterPhotoActivity
import br.com.edsb.hackathon.ui.adapters.ChatRecyclerAdapter
import kotlinx.android.synthetic.main.include_chat.*
import kotlinx.android.synthetic.main.include_chat.view.*

class RegisterUserActivity : BaseActivity(), RegisterUserInterfaces.View {
    private var presenter: RegisterUserPresenter? = null
    private val messages = ArrayList<ChatMessage>()
    private var adapter: ChatRecyclerAdapter? = null
    private var recyclerView: RecyclerView? = null
    private var userStep = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_user)

        adapter = ChatRecyclerAdapter(this, messages)
        recyclerView = rvChat
        recyclerView?.layoutManager = LinearLayoutManager(this)
        recyclerView?.adapter = adapter

        presenter = RegisterUserPresenter(this)

        btnSendMessage.setOnClickListener {
            val chatMessage = ChatMessage(view.etMessage.text.toString(), null,
                    0, false)
            registerMessage(chatMessage)
            presenter?.sendMessage(chatMessage.message, userStep)
            etMessage.setText("")
            userStep++
        }
    }

    override fun proceedToMenu() {
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
    }

    override fun proceedToPhoto() {
        val intent = Intent(this, RegisterPhotoActivity::class.java)
        intent.putExtra("fromMenu", true)
        startActivity(intent)
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

    override fun registerMessage(chatMessage: ChatMessage) {
        if (chatMessage.isBot) {
            sendMessageWithDelay(chatMessage, 1000)
        } else {
            if (chatMessage.options?.size ?: 0 == 0)
                addMessageToView(chatMessage)
            else
                sendMessageWithDelay(chatMessage, 1500)
        }

    }

    private fun sendMessageWithDelay(chatMessage: ChatMessage, delayMillis: Long) {
        Handler().postDelayed({ addMessageToView(chatMessage) }, delayMillis)
    }

    private fun addMessageToView(chatMessage: ChatMessage) {
        messages.add(chatMessage)
        adapter?.notifyItemInserted(messages.size)
        recyclerView?.scrollToPosition(adapter?.itemCount!! - 1)
        Log.e("MESSAGES", "$messages")
    }
}
