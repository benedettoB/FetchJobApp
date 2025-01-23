package com.benedetto.awsfetchjobs.ui.views

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.benedetto.awsfetchjobs.R
import com.benedetto.awsfetchjobs.ui.viewmodels.ItemViewModel
import com.benedetto.awsfetchjobs.ui.viewmodels.UiState
import com.benedetto.domain.models.Item

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    itemViewModel: ItemViewModel
) {
    val uiState by itemViewModel.uiState.collectAsState()
    val itemMap by itemViewModel.itemsState.collectAsState()

    MaterialTheme {
        when (uiState) {
            is UiState.Loading -> {
                LoadingSpinner(true, modifier)
            }

            is UiState.Success -> {
                LoadingSpinner(false, modifier)
                ItemsList(modifier, itemMap)
            }

            is UiState.GenericError -> {
                LoadingSpinner(false, modifier)
                ErrorScreen(modifier, false)
            }

            is UiState.NetworkError -> {
                LoadingSpinner(false, modifier)
                ErrorScreen(modifier, true)
            }

        }
    }

}


@Composable
fun ItemsList(modifier: Modifier, mappedItems: Map<Int, List<Item>>) {

    LazyColumn(modifier.padding(vertical = 4.dp)) {
        items(mappedItems.toList()) { (key, items) ->
            androidx.compose.material3.Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp)
            ) {

                CardContent(modifier, key, items)
            }
        }
    }
}


@Composable
fun CardContent(modifier: Modifier, key: Int, items: List<Item>) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    Row(
        modifier = modifier
            .padding(12.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column(
            modifier = modifier
                .weight(1f)
                .padding(12.dp)
        ) {

            Text(
                "Data For List Id: $key",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
            if (expanded) {
                items.forEach { item ->
                    Column(
                        modifier = modifier
                            .padding(vertical = 1.dp)
                    ) {
                        Text(
                            text = "List Id: ${item.listId}"
                        )
                        Text(
                            text = "Id: ${item.id}"
                        )
                        Text(
                            text = "Name: ${item.name}",
                        )
                    }

                }
            }

            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    imageVector = if (expanded) Filled.ExpandLess else Filled.ExpandMore,
                    contentDescription = if (expanded) {
                        stringResource(R.string.show_less)
                    } else {
                        stringResource(R.string.show_more)
                    }
                )
            }

        }
    }
}

@Composable
fun LoadingSpinner(isLoading: Boolean, modifier: Modifier = Modifier) {
    if (isLoading) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    isNetworkError: Boolean = false
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (isNetworkError) {

            Text(
                text = "Please connect to the internet. It is off.",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        } else {

            Text(
                text = "Something went wrong. Please try again later.",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}
