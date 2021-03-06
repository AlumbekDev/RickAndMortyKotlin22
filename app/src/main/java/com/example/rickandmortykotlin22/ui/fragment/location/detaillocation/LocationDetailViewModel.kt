package com.example.rickandmortykotlin22.ui.fragment.location.detaillocation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortykotlin22.data.network.dto.location.LocationDto
import com.example.rickandmortykotlin22.data.repositories.LocationRepository
import com.example.rickandmortykotlin22.keeper.resource.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LocationDetailViewModel @Inject constructor(
    private val repository: LocationRepository,
) : ViewModel() {

    private val _locationState = MutableStateFlow<Resource<LocationDto>>(Resource.Loading())
    val locationState: StateFlow<Resource<LocationDto>> = _locationState

    fun fetchLocation(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.fetchLocation(id).collect {
                _locationState.value = it
            }
        }
    }
}