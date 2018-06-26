package com.phen.firebasesensor

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_lista.*

class AlertsListAdapter(): RecyclerView.Adapter<AlertsListAdapter.ViewHolder>() {

    var list = mutableListOf<Alerts>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_lista, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun add(list: MutableList<Alerts>){
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun add(alert: Alerts){
        this.list.add(alert)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tv_humidity.text = list[position].humidity.toString()
        holder.tv_time.text = list[position].data
    }

    class ViewHolder(override val containerView: View?) : RecyclerView.ViewHolder(containerView), LayoutContainer { }
}
