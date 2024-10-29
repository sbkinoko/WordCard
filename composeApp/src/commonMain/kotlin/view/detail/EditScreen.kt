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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
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

    val focusRequesterList: MutableState<List<FocusRequester>> = remember {
        mutableStateOf(
            listOf()
        )
    }

    val scrollTo = remember {
        mutableStateOf(-1)
    }

    val updatedFlag = remember {
        mutableStateOf(false)
    }

    val addItem: (index: Int) -> Unit = { index ->
        focusRequesterList.value = List(listState.layoutInfo.totalItemsCount + 1) {
            FocusRequester()
        }
        editViewModel.insertAt(
            index = index,
        )
        scrollTo.value = index

        CoroutineScope(Dispatchers.Main).launch {
            do {
                delay(10)
                listState.scrollToItem(
                    index = scrollTo.value
                )
                // スクロールしたらflagが更新される
            } while (updatedFlag.value.not())
            delay(10)
            // 追加したものにfocusを合わせる
            focusRequesterList.value[scrollTo.value].requestFocus()
        }
    }

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(5.dp),
        state = listState,
    ) {
        itemsIndexed(
            itemList.value
        ) { index, detail ->
            val requester = if (
                focusRequesterList.value.size <= index
            ) {
                FocusRequester()
            } else {
                focusRequesterList.value[index]
            }
            DetailComponent(
                index = index,
                detail = detail,
                focusRequester = requester,
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
                    addItem(index)
                },
                onClickLowerAdd = {
                    addItem(index + 1)
                },
            )

            if (
                scrollTo.value == index
            ) {
                updatedFlag.value = true
            }
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
            addItem(listState.layoutInfo.totalItemsCount)
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
