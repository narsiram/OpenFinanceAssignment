package com.sumcorp.assignment.view.busList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sumcorp.assignment.R
import com.sumcorp.assignment.data.model.TimingInfo
import kotlinx.android.synthetic.main.item_bus_timing.view.*

class BusTimingAdapter : RecyclerView.Adapter<BusTimingAdapter.Holder>() {

    private var timingList: List<TimingInfo>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        Holder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_bus_timing, parent,
                false
            )
        )

    override fun onBindViewHolder(holder: Holder, position: Int) {
        if (timingList != null) {
            holder.onBind(timingList!![position])
        }
    }

    override fun getItemCount() = if (timingList != null)
        timingList!!.size
    else
        0

    fun setData(timingList: List<TimingInfo>?) {
        this.timingList = timingList
        notifyDataSetChanged()
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun onBind(timingInfo: TimingInfo) {
            itemView.tvSeatCount.text = "${timingInfo.availableSeats}/${timingInfo.totalSeats}"
            itemView.tvTime.text = timingInfo.startTime
        }
    }
}
