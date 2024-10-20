package view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import viewmodel.DetailViewModel

@Composable
fun EditScreen(
    modifier: Modifier = Modifier,
    detailViewModel: DetailViewModel = koinInject()
) {
    val itemList = detailViewModel.detailListState.collectAsState()

    val listState = rememberLazyListState()

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(5.dp),
        state = listState,
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
            .fillMaxWidth(),
        onClick = {
            CoroutineScope(Dispatchers.Main).launch {
                val index = listState.layoutInfo.totalItemsCount
                detailViewModel.add()
                delay(100)
                listState.scrollToItem(
                    index = index,
                )
            }
        },
    ) {
        Text(text = "+")
    }
}
