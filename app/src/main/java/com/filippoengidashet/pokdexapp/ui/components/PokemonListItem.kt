package com.filippoengidashet.pokdexapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.filippoengidashet.pokdexapp.domain.model.PokemonItem
import com.filippoengidashet.pokdexapp.ui.theme.Blue

@Composable
fun PokemonListItem(
    position: Int,
    item: PokemonItem,
    isFavourite: Boolean,
    onToggleFavourite: (isFavourite: Boolean) -> Unit,
    modifier: Modifier = Modifier,
    extraContent: @Composable ColumnScope.() -> Unit = {}
) {
    Column(
        modifier = modifier
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Spacer(modifier = Modifier.width(12.dp))

            Box(
                modifier = Modifier
                    .widthIn(min = 32.dp)
                    .height(32.dp)
                    .background(Blue, shape = CircleShape)
                    .padding(horizontal = 8.dp),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = position.toString(),
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(text = item.name, modifier = Modifier.weight(1f))

            IconToggleButton(
                checked = isFavourite,
                onCheckedChange = { checked ->
                    onToggleFavourite(checked)
                }
            ) {
                Icon(
                    imageVector = if (isFavourite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = null,
                    tint = if (isFavourite) Color.Red else Color.Gray
                )
            }
            Spacer(Modifier.width(8.dp))
        }

        extraContent()
    }
}

@Composable
@Preview(showBackground = true)
fun PokemonListItemPreview() {
    val item = PokemonItem(
        name = "Bulbasaur",
        url = "https://pokeapi.co/api/v2/pokemon/1/"
    )
    PokemonListItem(
        position = 15000,
        item = item,
        isFavourite = true,
        onToggleFavourite = {},
        modifier = Modifier.clickable { }
    )
}
