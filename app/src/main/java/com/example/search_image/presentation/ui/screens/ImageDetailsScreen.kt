package com.example.search_image.presentation.ui.screens

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.imageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.search_image.domain.entities.ResultEntity
import com.example.search_image.domain.entities.getTagTitles
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun ImageDetailsScreen(
    navigator: DestinationsNavigator,
    result: ResultEntity
) {

    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    Scaffold(modifier = Modifier, topBar = {
        AppBarComponent(navigator = navigator)
    },
        content = { paddingValues ->

            Box(Modifier.padding(paddingValues)) {
                if (isPortrait) {
                    Column(
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                    ) {
                        UserDetailsHeader(search = result)
                        LargeImage(search = result, LocalContext.current)
                        Spacer(modifier = Modifier.height(16.dp))
                        ImageInfoComponent(search = result)
                    }
                } else {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .verticalScroll(rememberScrollState())
                        ) {
                            UserDetailsHeader(search = result)
                            ImageInfoComponent(search = result)
                        }
                        Box(
                            modifier = Modifier.weight(2f)
                        ) {
                            LargeImage(search = result, LocalContext.current)
                        }

                    }
                }
            }

        }

    )
}

@Composable
fun SingleInfoItem(title: String, value: String?) {
    Column {
        Row(modifier = Modifier) {
            Text(text = "${title}: ", fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold)
            Text(text = value ?: "", fontFamily = FontFamily.Serif)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(color = Color.Gray.copy(alpha = 0.15f))
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun UserDetailsHeader(search: ResultEntity) {
    Row(
        modifier = Modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            elevation = 4.dp,
            shape = RoundedCornerShape(50.dp),
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(search.user?.profileImage?.medium ?: "")
                    .crossfade(true)
                    .build(),
                contentDescription = "item image",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp),
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = search.user?.username ?: "", textAlign = TextAlign.Center,
            fontSize = 16.sp,
        )
    }
}

@Composable
fun LargeImage(search: ResultEntity, context: Context) {
    val imageAspectRatio =
        (search.width?.toFloat() ?: 1f) / (search.height?.toFloat() ?: 1f)

    val isFullImageLoaded = remember { mutableStateOf(false) }

    if (isFullImageLoaded.value) {
        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current).data(data = search.urls?.full)
                    .apply(block = fun ImageRequest.Builder.() {
                        crossfade(true)
                    }).build()
            ),
            contentDescription = "item image",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(imageAspectRatio)
        )
    } else {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(context)
                .data(search.urls?.thumb)
                .crossfade(true)
                .build(),
            loading = {
                Box(
                    modifier = Modifier.size(50.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            },
            contentDescription = "item image",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(imageAspectRatio)
        )
    }

    LaunchedEffect(search.urls?.full) {
        if (!isFullImageLoaded.value) {
            val request = ImageRequest.Builder(context)
                .data(search.urls?.full)
                .build()
            val result = context.imageLoader.execute(request)

            if (result is SuccessResult) {
                isFullImageLoaded.value = true
            }
        }
    }
}


@Composable
fun ImageInfoComponent(search: ResultEntity) {
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
        SingleInfoItem(title = "Uploaded By", value = search.user?.firstName)
        SingleInfoItem(title = "Tags", value = search.tags.getTagTitles())
        SingleInfoItem(title = "Likes", value = search.likes.toString())
    }
}

@Composable
fun AppBarComponent(navigator: DestinationsNavigator) {
    TopAppBar(
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            Icons.Rounded.ArrowBack,
            contentDescription = "print",
            modifier = Modifier.clickable {
                navigator.popBackStack()
            }
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            "Image Details", textAlign = TextAlign.Center,
            fontSize = 16.sp,
        )
    }
}