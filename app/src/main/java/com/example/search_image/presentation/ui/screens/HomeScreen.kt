package com.example.search_image.presentation.ui.screens

import android.content.res.Configuration
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.search_image.R
import com.example.search_image.domain.entities.getTagTitles
import com.example.search_image.presentation.SearchEvent
import com.example.search_image.presentation.ui.components.ConnectedComponent
import com.example.search_image.presentation.ui.components.InfoComponent
import com.example.search_image.presentation.ui.components.ListItem
import com.example.search_image.presentation.ui.components.NotConnectedComponent
import com.example.search_image.presentation.ui.screens.destinations.ImageDetailsScreenDestination
import com.example.search_image.presentation.viewmodel.HomeScreenViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@RootNavGraph(start = true)
@Destination
@Composable
fun HomeScreen(navigator: DestinationsNavigator) {
    Scaffold(
        modifier = Modifier,
        topBar = {
            TopAppBar {
                Text(
                    "Search Images", textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        isFloatingActionButtonDocked = true,
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .background(color = Color.White)
            ) {
                BodyComponent(navigator)
            }
        }
    )
}

@Composable
fun BodyComponent(
    navigator: DestinationsNavigator,
    viewModel:
    HomeScreenViewModel = hiltViewModel(LocalContext.current as ComponentActivity)
) {
    val state = viewModel.state
    val openDialog = rememberSaveable { mutableStateOf(false) }
    val index = remember { mutableStateOf(0) }
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    val scrollState = rememberLazyGridState()
    val endReached by remember {
        derivedStateOf {
            !scrollState.canScrollForward
        }
    }

    if (endReached) {
        LaunchedEffect(Unit) {
            viewModel.onEvent(SearchEvent.GetMoreItems)
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        OutlinedTextField(
            value = state.searchQuery,
            onValueChange = {
                viewModel.onEvent(
                    SearchEvent.OnSearchQueryChange(it)
                )
            },
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                .fillMaxWidth(),
            placeholder = {
                Text(text = "Search...")
            },
            maxLines = 1,
            singleLine = true
        )
        if (state.searchLists.isNotEmpty()) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(if (isPortrait) 2 else 3),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, end = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp),
                state = scrollState
            ) {
                items(state.searchLists.size) { i ->
                    val search = state.searchLists[i]
                    Box(modifier = Modifier.clickable {
                        Log.w("detail_screen", "detail_screen/$i")
                        openDialog.value = true
                        index.value = i

                    }) {
                        ListItem(
                            search.user?.username ?: "", search.tags.getTagTitles(),
                            search.urls?.thumb ?: ""
                        )
                    }
                }

            }
        }


        if (state.isError) {
            InfoComponent(state.errorMessage)
        }

        if (state.isEmpty && !state.isLoading) {
            if (state.searchQuery.isEmpty()) {
                InfoComponent(stringResource(id = R.string.empty_first))
            } else {
                InfoComponent(stringResource(id = R.string.empty))
            }
        }

    }

    if (state.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(text = "Show More Details")
            },
            text = {
                Text(
                    "Do you want to see more details about this image?"
                )
            },
            buttons = {
                Row(
                    modifier = Modifier.padding(all = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            openDialog.value = false
                        }
                    ) {
                        Text("Dismiss")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            openDialog.value = false
                            navigator.navigate(
                                ImageDetailsScreenDestination(
                                    result = state.searchLists[index.value]
                                )
                            )
                        }
                    ) {
                        Text("Yes")
                    }
                }

            }
        )
    }

    if (state.showNetworkUnavailable) {
        NotConnectedComponent()
    }

    if (state.showNetworkConnected) {
        ConnectedComponent()
    }


}