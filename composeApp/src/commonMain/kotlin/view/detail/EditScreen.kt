package view.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import viewmodel.detail.EditViewModel

@Composable
fun EditScreen(
    modifier: Modifier = Modifier,
    editViewModel: EditViewModel = koinInject(),
    jumpTo: Int = 0,
) {
    val itemList = editViewModel
        .detailOrderState
        .collectAsState()

    val listState = rememberLazyListState()

    val idString = remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {
        editViewModel.init()
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
        ) { index, detail ->
            DetailComponent(
                index = index,
                detail = detail,
                update = { front, back, color ->
                    editViewModel.update(
                        id = detail.id,
                        front = front,
                        back = back,
                        color = color
                    )
                },
                delete = {
                    editViewModel.delete(
                        id = detail.id
                    )
                },
                onClickMove = {
                    editViewModel.move(
                        id = detail.id,
                        index = it,
                    )
                },
                onClickUpperAdd = {
                    editViewModel.insertAt(
                        index = index,
                    )
                    CoroutineScope(Dispatchers.Main).launch {
                        listState.scrollToItem(
                            index = index
                        )
                    }
                },
                onClickLowerAdd = {
                    editViewModel.insertAt(
                        index = index + 1
                    )
                    CoroutineScope(Dispatchers.Main).launch {
                        listState.scrollToItem(
                            index = index + 1
                        )
                    }
                },
            )
        }
    }

    NumberAndButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        value = idString.value,
        onValueChange = {
            idString.value = it
        },
        onClick = {
            CoroutineScope(Dispatchers.Main).launch {
                val num = idString.value.toIntOrNull()

                idString.value = ""

                if (num == null) {
                    return@launch
                }

                if (num < 0) {
                    return@launch
                }

                if (num >= itemList.value.size) {
                    listState.scrollToItem(
                        index = listState.layoutInfo.totalItemsCount
                    )
                    return@launch
                }

                listState.scrollToItem(
                    index = num,
                )
            }
        }
    )

    Button(
        modifier = Modifier
            .fillMaxWidth(),
        onClick = {
            CoroutineScope(Dispatchers.Main).launch {
                val index = listState.layoutInfo.totalItemsCount
                editViewModel.add()
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

@Composable
fun NumberAndButton(
    value: String,
    onValueChange: (String) -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        TextField(
            modifier = Modifier
                .weight(1f),
            value = value,
            onValueChange = onValueChange,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
        )

        Button(
            onClick = onClick
        ) {
            Text(text = "JUMP")
        }
    }
}
