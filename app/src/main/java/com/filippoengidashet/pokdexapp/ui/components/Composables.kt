package com.filippoengidashet.pokdexapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.filippoengidashet.pokdexapp.ui.theme.Red

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppToolbar(
    icon: @Composable () -> Unit = {},
    title: @Composable () -> Unit = {},
    actions: @Composable () -> Unit = {},
) {

    CenterAlignedTopAppBar(
        title = { title.invoke() },
        navigationIcon = { icon.invoke() },
        actions = { actions.invoke() },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Red,
        )
    )
}

@Composable
fun GroupTitle(title: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {

        Spacer(Modifier.height(8.dp))
        HorizontalDivider()
        Spacer(Modifier.height(8.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(Modifier.height(8.dp))
        HorizontalDivider()
        Spacer(Modifier.height(8.dp))
    }
}

@Composable
fun TextDetail(
    title: String,
    description: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            //.background(Brush.linearGradient(listOf(Color.LightGray, Color.White)))
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            modifier = modifier,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = description,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.weight(1f)
        )
    }
}
