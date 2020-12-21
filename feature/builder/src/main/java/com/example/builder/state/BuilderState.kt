package com.example.builder.state

import com.example.repository.models.ChampionBo
import com.example.repository.models.CompositionBo

sealed class BuilderState {

    data class RenderBuilder(val comp: CompositionBo, val champList: List<ChampionBo>) :
        BuilderState()

    data class RenderBuilderWithChamps(val comp: CompositionBo, val champList: List<ChampionBo>, val position: Int) :
        BuilderState()

    object RenderNewComp : BuilderState()

    object RenderEmpty : BuilderState()

    object Loading : BuilderState()

    object Error : BuilderState()
}