package com.example.builder.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.savedinstancestate.savedInstanceState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import com.example.builder.state.BuilderState
import com.example.builder.viewmodel.BuilderViewModel
import com.example.repository.models.ChampionBo
import com.example.repository.models.CompositionBo
import com.skydoves.landscapist.glide.GlideImage


@Composable
fun BuilderView(builderViewModel: BuilderViewModel, compId: Long?) {

    val state by builderViewModel.state.collectAsState()

    builderViewModel.initContent(compId)

    Card(shape = RectangleShape) {
        ConstraintLayout(Modifier.fillMaxWidth().fillMaxHeight()) {

            Column {
                Text(text = "Builder")
                when (state) {
                    is BuilderState.RenderNewComp -> {
                        Text(text = "Insert new comp name")
                        var inputText by savedInstanceState { "" }
                        TextField(value = inputText, onValueChange = { inputText = it })

                        Button(onClick = {
                            builderViewModel.setCompName(inputText)
                        },
                            content = {
                                Text(text = "OK")
                            })
                    }
                    is BuilderState.RenderBuilder -> {
                        val stateRender = state as BuilderState.RenderBuilder
                        Text(text = stateRender.comp.name)
                        RenderComp(stateRender.comp) { position ->
                            builderViewModel.selectChamp(position)
                        }
                    }
                    is BuilderState.RenderBuilderWithChamps -> {
                        val stateRender = state as BuilderState.RenderBuilderWithChamps
                        Text(text = stateRender.comp.name)
                        RenderComp(stateRender.comp) { position ->
                            builderViewModel.selectChamp(position)
                        }
                        RenderChamps(stateRender.champList) { champ ->
                            builderViewModel.addChamp(champ, stateRender.position)
                        }
                    }
                }
            }


        }
    }
}


@Composable
fun RenderChamps(champList: List<ChampionBo>, cells: Int = 5, onClick: (ChampionBo?) -> Unit) {
    val chunkedList = champList.chunked(cells)

    LazyColumn() {
        items(chunkedList) { champRow ->
            LazyRow(horizontalArrangement = Arrangement.SpaceAround) {
                items(champRow) { champ ->
                    ChampRow(champ = champ, onClick = { onClick(champ) })
                }
            }
        }
    }

}


@Composable
fun RenderComp(comp: CompositionBo, onClick: (Int) -> Unit) {
    LazyRow() {
        itemsIndexed(comp.champList) { index, item ->
            ChampRow(champ = item) { onClick(index) }
        }
    }
}

@Composable
private fun ChampRow(champ: ChampionBo?, onClick: (ChampionBo?) -> Unit) {
    Box(
        Modifier.clickable(onClick = { onClick(champ) })
    ) {
        if (champ != null) {
            GlideImage(
                modifier = Modifier.wrapContentWidth(),
                imageModel = champ.iconUrl,
                contentScale = ContentScale.Crop
            )
        } else {
            Image(bitmap = imageResource(android.R.drawable.ic_menu_add))
        }
    }
}