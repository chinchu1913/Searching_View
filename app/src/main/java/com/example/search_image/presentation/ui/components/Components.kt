package com.example.search_image.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.search_image.R
import com.example.search_image.ui.theme.ColorStatusConnected
import com.example.search_image.ui.theme.ColorStatusNotConnected

@Composable
fun NotConnectedComponent() {
    Box(
        modifier = Modifier
            .background(color = ColorStatusNotConnected)
            .padding(8.dp)
    ) {
        Text(
            text = stringResource(
                id = R.string.text_no_connectivity
            ),
            color = White,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
fun ConnectedComponent() {
    Box(
        modifier = Modifier
            .background(color = ColorStatusConnected)
            .padding(8.dp)
    ) {
        Text(
            text = stringResource(
                id = R.string.text_connectivity
            ),
            color = White,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
fun InfoComponent(msg: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Text(
            msg,
            style = MaterialTheme.typography.subtitle1.copy(fontSize = 14.sp),
            textAlign = TextAlign.Center
        )
    }
}
