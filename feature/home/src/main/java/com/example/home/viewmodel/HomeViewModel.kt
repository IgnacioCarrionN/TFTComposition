package com.example.home.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.home.state.HomeState
import com.example.repository.CompBuilderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel @ViewModelInject constructor(
    private val repository: CompBuilderRepository
) : ViewModel() {

    private val _state = MutableStateFlow<HomeState>(HomeState.Loading)
    val state: StateFlow<HomeState>
        get() = _state

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateChampList()
        }
    }

    fun getCompList() {
        viewModelScope.launch(Dispatchers.IO) {
            val compList = repository.getCompList()
            compList.collect {
                if (it.isNotEmpty()) {
                    _state.value = HomeState.RenderCompList(it)
                } else {
                    _state.value = HomeState.RenderEmpty
                }
            }
        }
    }

}