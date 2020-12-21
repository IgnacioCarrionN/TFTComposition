package com.example.builder.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.builder.state.BuilderState
import com.example.repository.CompBuilderRepository
import com.example.repository.models.ChampionBo
import com.example.repository.models.CompositionBo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class BuilderViewModel @ViewModelInject constructor(
    private val repository: CompBuilderRepository
) : ViewModel() {

    private val _state: MutableStateFlow<BuilderState> = MutableStateFlow(BuilderState.RenderEmpty)
    val state: StateFlow<BuilderState>
        get() = _state

    private var job: Job? = null

    fun initContent(compId: Long?) {
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            if (compId != null) {
                getCompById(compId)
            } else {
                handleNewCompState()
            }
        }
    }

    private suspend fun getCompById(compId: Long) {
        repository.getChampList().combine(repository.getComp(compId)) { champList, comp ->
            Pair(champList, comp)
        }.collect {
            handleState(it.first, it.second)
        }
    }

    private suspend fun handleNewCompState() {
        _state.value = BuilderState.RenderNewComp
    }

    fun setCompName(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val currentState = _state.value
            if (currentState is BuilderState.RenderNewComp) {
                createComp(name)
            }
        }
    }

    fun selectChamp(position: Int) {
        val state = _state.value
        if (state is BuilderState.RenderBuilder) {
            _state.value =
                BuilderState.RenderBuilderWithChamps(state.comp, state.champList, position)
        }
    }

    private suspend fun createComp(name: String) {
        repository.getChampList().combine(repository.createComp(name)) { champList, comp ->
            Pair(champList, comp)
        }.collect {
            handleState(it.first, it.second)
        }

    }

    private fun handleState(champList: List<ChampionBo>, comp: CompositionBo?) {
        if (comp != null) {
            _state.value = BuilderState.RenderBuilder(comp, champList)
        } else {
            _state.value = BuilderState.Error
        }
    }

    fun deleteComp() {
        viewModelScope.launch(Dispatchers.IO) {
            val currentState = _state.value
            if (currentState is BuilderState.RenderBuilder) {
                val comp = currentState.comp
                repository.deleteComp(comp)
            }
        }
    }

    fun addChamp(championBo: ChampionBo?, position: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val currentState = _state.value
            if (currentState is BuilderState.RenderBuilderWithChamps) {
                val compChamps = currentState.comp.champList.toMutableList()
                compChamps[position] = championBo
                val newComp = currentState.comp.copy(champList = compChamps)
                repository.setComp(newComp)
            }
        }
    }

    fun removeChamp(championBo: ChampionBo) {
        viewModelScope.launch(Dispatchers.IO) {
            val currentState = _state.value
            if (currentState is BuilderState.RenderBuilder) {
                val compChamps = currentState.comp.champList.toMutableList()
                compChamps.remove(championBo)

                val newComp = currentState.comp.copy(champList = compChamps)
                repository.setComp(newComp)
            }
        }
    }

}