package br.com.edsb.hackathon.ui.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.edsb.hackathon.R
import br.com.edsb.hackathon.data.model.Pill
import kotlinx.android.synthetic.main.item_pill.view.*

class PillRecyclerAdapter(private val context: Context,
                          private val items: ArrayList<Pill>) :
        RecyclerView.Adapter<PillRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_pill, parent,
                false)
        return ViewHolder(view, context)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bindView(items[position])
    }

    class ViewHolder(itemView: View, private val context: Context) :
            RecyclerView.ViewHolder(itemView) {

        fun bindView(item: Pill) {
            with(item) {
                itemView.tvPill.text = item.pill
                itemView.civIcon.setImageResource(item.icon)

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

    fun setOnItemClickListener(onClick:
                               ((view: View?, position: Int, item: Pill) -> Any?)?) {
        PillRecyclerAdapter.onClick = onClick
    }

    fun setOnItemLongClickListener(onLongClick:
                                   ((view: View?, position: Int, item: Pill) -> Any?)?) {
        PillRecyclerAdapter.onLongClick = onLongClick
    }

    companion object {
        internal var onClick: ((view: View?, position: Int, item: Pill) -> Any?)? = null
        internal var onLongClick: ((view: View?, position: Int, item: Pill) -> Any?)? = null
    }
}