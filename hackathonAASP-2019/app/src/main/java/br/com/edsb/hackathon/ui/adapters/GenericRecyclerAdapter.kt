package br.com.edsb.hackathon.ui.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import br.com.edsb.hackathon.R
import br.com.edsb.hackathon.data.model.GenericItemModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.item_generic.view.*

class GenericRecyclerAdapter(private val context: Context,
                             private val items: ArrayList<GenericItemModel>) :
        RecyclerView.Adapter<GenericRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_generic, parent,
                false)
        return ViewHolder(view, context)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bindView(items[position])
    }

    class ViewHolder(itemView: View, private val context: Context) :
            RecyclerView.ViewHolder(itemView) {
        private val tvContent: TextView = itemView.tvContent
        private val ivIcon: CircleImageView = itemView.ivPhoto
        private val tvDescription: TextView = itemView.tvDescription
        private val ivAdmin: ImageView = itemView.ivAdmin

        fun bindView(item: GenericItemModel) {
            with(item) {
                Log.e("ITEM ITEM", extraArgs?.get("admin").toString())
                if (extraArgs?.get("admin") == true)
                    ivAdmin.visibility = VISIBLE
                else
                    ivAdmin.visibility = GONE

                tvContent.text = contentText

                tvDescription.text = descriptionText

                photoUrl?.addOnSuccessListener {
                    Log.e("URL", it.toString())
                    Glide.with(context)
                            .load(it)
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                            .into(ivIcon)
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

    fun setOnItemClickListener(onClick:
                               ((view: View?, position: Int, item: GenericItemModel) -> Any?)?) {
        GenericRecyclerAdapter.onClick = onClick
    }

    fun setOnItemLongClickListener(onLongClick:
                                   ((view: View?, position: Int, item: GenericItemModel) -> Any?)?) {
        GenericRecyclerAdapter.onLongClick = onLongClick
    }

    companion object {
        internal var onClick: ((view: View?, position: Int, item: GenericItemModel) -> Any?)? = null
        internal var onLongClick: ((view: View?, position: Int, item: GenericItemModel) -> Any?)? = null
    }
}