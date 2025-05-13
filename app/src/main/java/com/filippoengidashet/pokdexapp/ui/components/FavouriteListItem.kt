package com.filippoengidashet.pokdexapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.filippoengidashet.pokdexapp.domain.model.FavoriteItem

@Composable
fun FavouriteListItem(
    item: FavoriteItem,
    onDelete: () -> Unit,
    onNavigateToDetail: () -> Unit,
) {
    Column(
        Modifier
            .fillMaxWidth()
            .clickable {
                onNavigateToDetail.invoke()
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(item.name, Modifier.weight(1f))

            IconButton(
                onClick = {
                    onDelete.invoke()
                },
                modifier = Modifier.testTag(DELETE_FAVOURITE_BUTTON_TEST_TAG)
            ) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Delete Favorite"
                )
            }
        }

        HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
    }
}

const val DELETE_FAVOURITE_BUTTON_TEST_TAG = "delete_favourite_button"

@Composable
@Preview(showBackground = true)
fun FavouriteListItemPreview() {
    FavouriteListItem(FavoriteItem(""), {}, {})
}
