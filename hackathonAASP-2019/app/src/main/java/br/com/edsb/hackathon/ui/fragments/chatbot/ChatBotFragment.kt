package br.com.edsb.hackathon.ui.fragments.chatbot

import ai.api.android.AIService
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.WindowManager
import br.com.edsb.hackathon.R
import br.com.edsb.hackathon.data.model.ChatMessage
import br.com.edsb.hackathon.ui.activities.base.BaseFragment
import br.com.edsb.hackathon.ui.adapters.ChatRecyclerAdapter
import kotlinx.android.synthetic.main.include_chat.view.*


class ChatBotFragment : BaseFragment(), ChatBotInterfaces.View {
    var aiService: AIService? = null
    private var listener: OnFragmentInteractionListener? = null
    private var presenter : ChatBotPresenter? = null
    private val messages = ArrayList<ChatMessage>()
    private var adapter: ChatRecyclerAdapter? = null
    private var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_chatbot, container, false)

        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        adapter = ChatRecyclerAdapter(context, messages)
        recyclerView = view.rvChat
        recyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView?.adapter = adapter

        presenter = ChatBotPresenter((this))


        view.btnSendMessage.setOnClickListener {
            Log.e("TESTE CLICK", "CLICK")
            val chatMessage = ChatMessage(view.etMessage.text.toString(), null, 0, false)
            registerMessage(chatMessage)
            presenter?.sendMessage(chatMessage.message)
            view.etMessage.setText("")
        }


        return view
    }

    override fun registerMessage(chatMessage: ChatMessage) {
        messages.add(chatMessage)
        adapter?.notifyItemInserted(messages.size)
        recyclerView?.scrollToPosition(adapter?.itemCount!! - 1)
        Log.e("MESSAGES", "$messages")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() +
                    " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }
}
