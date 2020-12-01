package com.sumcorp.assignment.view.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sumcorp.assignment.data.dataSource.LocalDataSource
import com.sumcorp.assignment.data.local.entity.BusInfo
import com.sumcorp.assignment.data.model.TimingInfo
import kotlinx.coroutines.launch

class BusListViewModel(val localDataSource: LocalDataSource) : ViewModel() {

    private val _busList = MutableLiveData<List<BusInfo>>()
    val busList: LiveData<List<BusInfo>>
        get() = _busList

    private val _tripTiming = MutableLiveData<List<TimingInfo>>()

    val tripTiming: LiveData<List<TimingInfo>>
        get() = _tripTiming

    fun fetchTripTiming(busId: String) {
        viewModelScope.launch {
            _tripTiming.postValue(localDataSource.getTripTimings(busId))
        }
    }

    fun fetchBusList() {
        viewModelScope.launch {
            _busList.postValue(localDataSource.getBusesFromDB())
        }
    }

}