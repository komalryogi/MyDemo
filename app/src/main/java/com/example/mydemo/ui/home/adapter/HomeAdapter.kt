package com.example.mydemo.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mydemo.R
import com.example.mydemo.data.entity.LockInfo
import kotlinx.android.synthetic.main.item_info.view.*

class HomeAdapter(val items: MutableList<LockInfo>, val context: Context?) :
    RecyclerView.Adapter<ViewHolder>() {

    private lateinit var listener: InfoClickedListener

    override fun getItemCount(): Int {
        return items.size
    }

    fun setInfoListener(listener: InfoClickedListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_info, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = items.get(position)
        holder?.btn_name?.text = model.name
        holder?.btn_name.setOnClickListener {
            if (position <= 1)
                if (listener != null) {
                    listener.onInfoClickedListener(model)
                }
        }

    }

    public interface InfoClickedListener {
        fun onInfoClickedListener(info: LockInfo)
    }

}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val btn_name = view.btn_name
}

