package br.com.edsb.hackathon.ui.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import br.com.edsb.hackathon.R
import br.com.edsb.hackathon.data.model.Road
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.item_road.view.*

class RoadRecyclerAdapter(private val context: Context,
                          private val items: ArrayList<Road>) :
        RecyclerView.Adapter<RoadRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_road, parent,
                false)
        return ViewHolder(view, context)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bindView(items[position])
    }

    class ViewHolder(itemView: View, private val context: Context) :
            RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.tvTitle
        private val civIcon: CircleImageView = itemView.civIcon

        fun bindView(item: Road) {
            with(item) {
                tvTitle.text = item.title
                civIcon.setImageResource(item.icon)

                if (item.enabled)
                    itemView.disabled.visibility = GONE
                else
                    itemView.disabled.visibility = VISIBLE

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
                               ((view: View?, position: Int, item: Road) -> Any?)?) {
        RoadRecyclerAdapter.onClick = onClick
    }

    fun setOnItemLongClickListener(onLongClick:
                                   ((view: View?, position: Int, item: Road) -> Any?)?) {
        RoadRecyclerAdapter.onLongClick = onLongClick
    }

    companion object {
        internal var onClick: ((view: View?, position: Int, item: Road) -> Any?)? = null
        internal var onLongClick: ((view: View?, position: Int, item: Road) -> Any?)? = null
    }
}