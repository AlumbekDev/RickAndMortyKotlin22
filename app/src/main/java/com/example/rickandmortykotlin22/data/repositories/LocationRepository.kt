package com.example.rickandmortykotlin22.data.repositories

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.rickandmortykotlin22.data.network.apiservice.LocationApiService
import com.example.rickandmortykotlin22.data.network.dto.location.LocationDto
import com.example.rickandmortykotlin22.data.network.paginsources.LocationPagingSource
import com.example.rickandmortykotlin22.keeper.base.BaseRepository
import javax.inject.Inject

class LocationRepository @Inject constructor(
    private val service: LocationApiService
): BaseRepository() {

    fun fetchLocations(): LiveData<PagingData<LocationDto>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
            ),
            pagingSourceFactory = {
                LocationPagingSource(service)
            }
        ).liveData
    }

    fun fetchLocation(id: Int) = doRequest {
        service.fetchLocation(id)
    }
}