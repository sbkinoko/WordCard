package view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import viewmodel.DetailViewModel

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    detailViewModel: DetailViewModel = koinInject(),
) {
    val focusManager = LocalFocusManager.current
    val itemList = detailViewModel.detailListState.collectAsState()

    val title = detailViewModel.titleFlow.collectAsState()

    LaunchedEffect(Unit) {
        detailViewModel.setId()
    }

    Column(
        modifier = modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
            ) {
                focusManager.clearFocus()
            }
            .padding(10.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 5.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = title.value
            )
        }
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(
                itemList.value
            ) {
                DetailComponent(
                    detail = it,
                    update = { front, back, color ->
                        detailViewModel.update(
                            id = it.id,
                            front = front,
                            back = back,
                            color = color
                        )
                    },
                    delete = {
                        detailViewModel.delete(
                            id = it.id
                        )
                    }
                )
            }
        }
        Button(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .fillMaxWidth(),
            onClick = {
                detailViewModel.add()
            }
        ) {
            Text(text = "+")
        }
        Row {
            Button(
                modifier = Modifier
                    .padding(horizontal = 10.dp),
                onClick = {
                    detailViewModel.reset()
                },
            ) {
                Text(
                    "←"
                )
            }
            Spacer(
                modifier = Modifier.weight(1f)
            )
        }
    }
}
