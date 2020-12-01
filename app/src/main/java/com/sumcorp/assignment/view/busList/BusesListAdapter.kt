package com.sumcorp.assignment.view.busList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sumcorp.assignment.R
import com.sumcorp.assignment.data.local.entity.BusInfo
import kotlinx.android.synthetic.main.item_bus_info.view.*

class BusesListAdapter : RecyclerView.Adapter<BusesListAdapter.Holder>() {


    private var busesList: List<BusInfo>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        Holder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_bus_info, parent,
                false
            )
        )

    override fun onBindViewHolder(holder: Holder, position: Int) {
        if (busesList != null) {
            holder.onBind(busesList!!.get(position))
        }
    }

    override fun getItemCount(): Int {
        if (busesList != null) {
            return busesList!!.size
        } else
            return 0
    }

    fun setData(busesList: List<BusInfo>) {
        this.busesList = busesList
        notifyDataSetChanged()
    }

    class Holder(itemView:   View) : RecyclerView.ViewHolder(itemView) {

        fun onBind(busInfo: BusInfo) {
            itemView.tvBusName.text = busInfo.name
            itemView.tvBusRoute.text = "${busInfo.source} - ${busInfo.desination}"
            itemView.tvTotalDuration.text = busInfo.tripDuration
        }
    }
}