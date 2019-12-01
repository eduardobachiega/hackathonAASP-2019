package br.com.edsb.hackathon.ui.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import br.com.edsb.hackathon.R
import br.com.edsb.hackathon.data.model.ChatMessage
import kotlinx.android.synthetic.main.item_bot_message.view.*
import kotlinx.android.synthetic.main.item_chat_multiple_responses.view.*

class ChatRecyclerAdapter(private val context: Context,
                          private val items: ArrayList<ChatMessage>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_MESSAGE_SENT = 1
    private val VIEW_TYPE_MESSAGE_RECEIVED = 2
    private val VIEW_TYPE_MULTIPLE_ACTIONS = 3

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view: View

        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_user_message, parent, false)
            return SentMessageHolder(view, context)
        } else if (viewType == VIEW_TYPE_MULTIPLE_ACTIONS) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_chat_multiple_responses, parent, false)
            return ChatMultipleHolder(view, context)
        }

        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bot_message, parent, false)
        return ReceivedMessageHolder(view, context)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            VIEW_TYPE_MESSAGE_SENT -> (viewHolder as SentMessageHolder).bindView(items[position])
            VIEW_TYPE_MESSAGE_RECEIVED -> (viewHolder as ReceivedMessageHolder).bindView(items[position])
            VIEW_TYPE_MULTIPLE_ACTIONS -> (viewHolder as ChatMultipleHolder).bindView(items[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        val messageItem = items[position]

        return if (messageItem.isBot)
            VIEW_TYPE_MESSAGE_RECEIVED
        else if (!messageItem.options.isNullOrEmpty())
            VIEW_TYPE_MULTIPLE_ACTIONS
        else
            VIEW_TYPE_MESSAGE_SENT
    }

    class SentMessageHolder(itemView: View, private val context: Context) :
            RecyclerView.ViewHolder(itemView) {

        fun bindView(item: ChatMessage) {
            with(item) {
                itemView.tvMessage.text = item.message

                itemView.setOnClickListener {
                    onClick?.invoke(it, adapterPosition, this)
                }

                itemView.setOnLongClickListener {
                    onLongClick?.invoke(it, adapterPosition, this)

                    true
                }
            }
        }
    }

    class ReceivedMessageHolder(itemView: View, private val context: Context) :
            RecyclerView.ViewHolder(itemView) {

        fun bindView(item: ChatMessage) {
            with(item) {
                itemView.tvMessage.text = item.message
                itemView.tvReceivedUser.text = item.username
                itemView.ivReceivedProfile.setImageResource(item.image)

                itemView.setOnClickListener {
                    onClick?.invoke(it, adapterPosition, this)
                }

                itemView.setOnLongClickListener {
                    onLongClick?.invoke(it, adapterPosition, this)

                    true
                }
            }
        }
    }

    class ChatMultipleHolder(itemView: View, private val context: Context) :
            RecyclerView.ViewHolder(itemView) {

        fun bindView(item: ChatMessage) {
            with(item) {
                for (i in 0 until item.options?.size!!) {
                    val optionItem = Button(context)
                    optionItem.text = item.options[i]
                    optionItem.width = 5

                    optionItem.setOnClickListener {
                        onListClickListener?.invoke(it, adapterPosition, i, this)
                    }

                    itemView.lnlMain.addView(optionItem)
                }

                itemView.setOnClickListener {
                    onClick?.invoke(it, adapterPosition, this)
                }

                itemView.setOnLongClickListener {
                    onLongClick?.invoke(it, adapterPosition, this)

                    true
                }
            }
        }
    }

    fun setOnListClickListener(onListClickListener: ((view: View?, position: Int, listPosition: Int,
                                                      item: ChatMessage) -> Any?)?) {
        ChatRecyclerAdapter.onListClickListener = onListClickListener
    }

    fun setOnItemClickListener(onClick:
                               ((view: View?, position: Int, item: ChatMessage) -> Any?)?) {
        ChatRecyclerAdapter.onClick = onClick
    }

    fun setOnItemLongClickListener(onLongClick:
                                   ((view: View?, position: Int, item: ChatMessage) -> Any?)?) {
        ChatRecyclerAdapter.onLongClick = onLongClick
    }

    companion object {
        internal var onClick: ((view: View?, position: Int, item: ChatMessage) -> Any?)? = null
        internal var onLongClick: ((view: View?, position: Int, item: ChatMessage) -> Any?)? = null
        internal var onListClickListener: ((view: View?, position: Int, listPosition: Int, item: ChatMessage) -> Any?)? = null
    }
}