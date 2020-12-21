package com.example.home.state

import com.example.repository.models.CompositionBo

sealed class HomeState {

    data class RenderCompList(val data: List<CompositionBo>) : HomeState()

    object RenderEmpty : HomeState()

    object Loading : HomeState()

    object Error : HomeState()

}