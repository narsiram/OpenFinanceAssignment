package com.sumcorp.assignment.view.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sumcorp.assignment.data.dataSource.LocalDataSource

@Suppress("UNCHECKED_CAST")
class BusListViewModelFactory(val localDataSource: LocalDataSource) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BusListViewModel(localDataSource) as T
    }
}