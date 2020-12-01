package com.sumcorp.assignment.view.busList

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.*
import com.sumcorp.assignment.R
import com.sumcorp.assignment.data.dataSource.LocalDataSource
import com.sumcorp.assignment.data.local.database.BusListDatabase
import com.sumcorp.assignment.data.local.entity.BusInfo
import com.sumcorp.assignment.data.local.entity.RouteInfo
import com.sumcorp.assignment.data.model.TimingInfo
import com.sumcorp.assignment.view.viewModel.BusListViewModel
import com.sumcorp.assignment.view.viewModel.BusListViewModelFactory
import kotlinx.android.synthetic.main.activity_buses_list.*
import java.text.SimpleDateFormat
import java.util.*


class BusesListActivity : AppCompatActivity() {

    private lateinit var busListAdapter: BusesListAdapter
    private lateinit var busTimingAdapter: BusTimingAdapter
    private lateinit var viewModel: BusListViewModel
    private lateinit var busesList: List<BusInfo>
    private lateinit var timingList: List<TimingInfo>

    private var minuteUpdateReceiver: BroadcastReceiver? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buses_list)

        setTitle(getString(R.string.bus_timings))

        setUpBusListRecyclerView()
        setUpTimingListRecyclerView()
        setUpViewModel()
        observeViewModelData()
    }

    private fun observeViewModelData() {
        viewModel.busList.observe(this, {

            it?.let {
                busesList = it
                setBusListAdapter(busesList)
                getBusTimings(busesList[0].id)
            }
        })


        viewModel.tripTiming.observe(this, {

            it?.let {
                timingList = it
                setTimingDataToAdapter()
            }
        })

    }

    private fun getBusTimings(busId: String) {
        viewModel.fetchTripTiming(busId)
    }

    private fun setTimingDataToAdapter() {
        val currentTime: String =
            SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())

        timingList = timingList.filter { it.startTime > currentTime }

        if (timingList.size > 0) {
            recyclerViewTimings.visibility = View.VISIBLE
            tvNoTiming.visibility = View.GONE

            timingList = timingList.sortedByDescending { it.startTime }.reversed()
            busTimingAdapter.setData(timingList)
        } else {
            recyclerViewTimings.visibility = View.GONE
            tvNoTiming.visibility = View.VISIBLE
        }

    }

    private fun setBusListAdapter(busesList: List<BusInfo>) {

        busListAdapter.setData(busesList)
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(
            this, BusListViewModelFactory(
                LocalDataSource(
                    this,
                    BusListDatabase.getInstance(this).getBusListDao(),
                    BusListDatabase.getInstance(this).getTripTimingDao()
                )
            )
        )[BusListViewModel::class.java]

        viewModel.fetchBusList()
    }

    private fun setUpTimingListRecyclerView() {
        recyclerViewTimings.run {
            setHasFixedSize(true)
            itemAnimator = DefaultItemAnimator()
            busTimingAdapter = BusTimingAdapter()
            adapter = busTimingAdapter
        }
    }

    private fun setUpBusListRecyclerView() {
        recyclerViewBuses.run {
            setHasFixedSize(true)
            val layoutHorizontalManager =
                LinearLayoutManager(this@BusesListActivity, RecyclerView.HORIZONTAL, false)

            layoutManager = layoutHorizontalManager
            itemAnimator = DefaultItemAnimator()
            busListAdapter = BusesListAdapter()
            adapter = busListAdapter
            var previousItem = 0

            //Making RecyclerView as ViewPager with the help of SnapHelper....
            val snapHelper: SnapHelper = PagerSnapHelper()
            snapHelper.attachToRecyclerView(this)

            addOnScrollListener(object : RecyclerView.OnScrollListener() {

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val firstVisibleItemPosition: Int =
                        layoutHorizontalManager.findFirstVisibleItemPosition()
                    if (previousItem != firstVisibleItemPosition) {
                        getBusTimings(busesList[firstVisibleItemPosition].id)
                        previousItem = firstVisibleItemPosition
                    }
                }
            })
        }
    }

    override fun onResume() {
        super.onResume()
        startMinuteUpdater()
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(minuteUpdateReceiver)
    }

    fun startMinuteUpdater() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(Intent.ACTION_TIME_TICK)
        minuteUpdateReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {

                setTimingDataToAdapter()
            }
        }
        registerReceiver(minuteUpdateReceiver, intentFilter)
    }
}