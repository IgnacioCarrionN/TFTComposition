package com.example.home.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.example.home.R
import com.example.home.state.HomeState
import com.example.home.viewmodel.HomeViewModel
import com.example.repository.models.ChampionBo
import com.example.repository.models.CompositionBo
import com.skydoves.landscapist.glide.GlideImage


@Composable
fun HomeView(navController: NavController, homeViewModel: HomeViewModel) {

    val state: HomeState by homeViewModel.state.collectAsState()

    homeViewModel.getCompList()


    Card(Modifier.fillMaxHeight().fillMaxWidth(), shape = RectangleShape) {
        ConstraintLayout(
            modifier = Modifier.fillMaxHeight().fillMaxWidth()
        ) {
            val (card, fab) = createRefs()
            Card(
                modifier = Modifier.fillMaxHeight().fillMaxWidth()
            ) {
                when (state) {
                    is HomeState.RenderCompList -> RenderCompList((state as HomeState.RenderCompList).data) {
                        handleNavigationToComp(
                            navController,
                            it
                        )
                    }
                    is HomeState.Loading -> RenderLoading()
                    is HomeState.RenderEmpty -> RenderEmptyList()
                    is HomeState.Error -> RenderError()
                }
            }

            FloatingActionButton(
                onClick = { navController.navigate("builder/?compId=") },
                modifier = Modifier.constrainAs(fab) {
                    bottom.linkTo(parent.bottom, margin = 16.dp)
                    absoluteRight.linkTo(parent.absoluteRight, margin = 16.dp)
                }) {

                Icon(Icons.Filled.Add)

            }

        }
    }

}

private fun handleNavigationToComp(navController: NavController, compId: Long) {
    navController.navigate("builder/?compId=$compId")
}

@Composable
fun RenderCompList(compList: List<CompositionBo>, onClick: (Long) -> Unit) {
    Column() {
        Text(
            text = stringResource(id = R.string.home_title),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(vertical = 20.dp)
        )
        LazyColumn {
            items(items = compList, itemContent = { item ->
                CompRow(comp = item, onClick)
                Divider(color = Color.Black, thickness = 1.dp, modifier = Modifier.padding(vertical = 12.dp))
            })
        }
    }
}

@Composable
fun CompRow(comp: CompositionBo, onClick: (Long) -> Unit) {
    Row(Modifier.clickable(onClick = { onClick(comp.id) }).fillMaxWidth()) {
        Column() {
            Text(text = comp.name, modifier = Modifier.padding(bottom = 12.dp, start = 12.dp), fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            LazyRow(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                items(comp.champList) {
                    ChampRow(champ = it)
                }
            }
        }
    }
}

@Composable
fun ChampRow(champ: ChampionBo?) {
    Row {
        Card(Modifier.size(30.dp), border = BorderStroke(1.dp, Color.Black)) {
            if (champ != null) {
                GlideImage(
                    imageModel = champ.iconUrl,
                    contentScale = ContentScale.FillWidth
                )
            } else {
                Image(bitmap = imageResource(android.R.drawable.ic_menu_add))
            }
        }
    }
}

@Composable
fun RenderLoading() {
    Row(Modifier.fillMaxHeight().fillMaxWidth()) {
        CircularProgressIndicator(modifier = Modifier.wrapContentWidth(Alignment.CenterHorizontally))
    }
}

@Composable
fun RenderEmptyList() {
    Row(Modifier.fillMaxWidth().fillMaxHeight()) {
        Text(text = stringResource(id = R.string.home_empty_comps))
    }
}

@Composable
fun RenderError() {
    // TODO
}

@Preview
@Composable
fun PreviewLoading() {
    RenderLoading()
}