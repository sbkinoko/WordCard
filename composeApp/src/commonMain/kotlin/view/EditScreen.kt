package view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
    detailViewModel: DetailViewModel = koinInject(),
    jumpTo: Int = 0,
) {
    val itemList = detailViewModel.detailListState.collectAsState()

    val listState = rememberLazyListState()

    val idString = remember {
        mutableStateOf("")
    }

    LaunchedEffect(jumpTo) {
        if (jumpTo < 0) {
            return@LaunchedEffect
        }

        if (jumpTo >= itemList.value.size) {
            return@LaunchedEffect
        }

        listState.scrollToItem(
            index = jumpTo,
        )
    }

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(5.dp),
        state = listState,
    ) {
        itemsIndexed(
            itemList.value
        ) { index, it ->
            DetailComponent(
                index = index,
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

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        TextField(
            modifier = Modifier
                .weight(1f),
            value = idString.value,
            onValueChange = {
                idString.value = it
            }
        )

        Button(
            onClick = {
                CoroutineScope(Dispatchers.Main).launch {
                    idString.value.toIntOrNull()?.let {
                        if (it < 0) {
                            return@let
                        }

                        if (it >= itemList.value.size) {
                            return@let
                        }

                        listState.scrollToItem(
                            index = it,
                        )
                    }
                }
            }
        ) {
            Text(text = "JUMP")
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
