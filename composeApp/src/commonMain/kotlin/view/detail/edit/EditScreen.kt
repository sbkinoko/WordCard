package view.detail.edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.mongodb.kbson.ObjectId
import view.detail.DetailComponent
import view.dialog.DeleteDialog
import viewmodel.detail.EditViewModel

@Composable
fun EditScreen(
    modifier: Modifier = Modifier,
    editViewModel: EditViewModel = koinInject(),
    jumpTo: ObjectId = ObjectId(),
) {
    val focusManager = LocalFocusManager.current

    val itemList = editViewModel
        .detailOrderState
        .collectAsState()

    val listState = rememberLazyListState()

    LaunchedEffect(Unit) {
        editViewModel.init()
    }

    val dialogState = remember {
        mutableStateOf(false)
    }
    val deleteId = remember {
        mutableStateOf(ObjectId())
    }

    LaunchedEffect(jumpTo) {
        val index = editViewModel.getIndexOf(
            id = jumpTo
        )

        if (index < 0) {
            return@LaunchedEffect
        }

        if (index >= itemList.value.size) {
            return@LaunchedEffect
        }

        listState.scrollToItem(
            index = index,
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

    val scroll = {
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

    val addItem: (index: Int, position: Int) -> Unit = { index, position ->
        val afterNum = listState.layoutInfo.totalItemsCount + 1
        focusRequesterList.value = List(afterNum) {
            FocusRequester()
        }
        updatedFlag.value = false

        editViewModel.insertAt(
            index = index,
            position = position,
        )
        scrollTo.value = index + position

        scroll()
    }

    val addItemAtLast: () -> Unit = {
        val afterNum = listState.layoutInfo.totalItemsCount + 1
        focusRequesterList.value = List(afterNum) {
            FocusRequester()
        }
        updatedFlag.value = false
        editViewModel.insertAtLast()
        scrollTo.value = afterNum - 1

        scroll()
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
                index = editViewModel.getActualIndex(detail.id),
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
                    dialogState.value = true
                    deleteId.value = detail.id
                },
                onClickMove = {
                    editViewModel.move(
                        id = detail.id,
                        index = it,
                    )
                },
                onClickUpperAdd =  {
                    focusManager.clearFocus()
                    addItem(index, 0)
                },
                onClickLowerAdd =  {
                    focusManager.clearFocus()
                    addItem(index, 1)
                },
            )

            if (
                scrollTo.value == index
            ) {
                updatedFlag.value = true
            }
        }
    }

    Button(
        modifier = Modifier
            .fillMaxWidth(),
        onClick =  {
            focusManager.clearFocus()
            addItemAtLast()
        },
    ) {
        Text(text = "+")
    }

    BottomButton(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(5.dp),
        onClickJump = { num ->
            focusManager.clearFocus()
            CoroutineScope(Dispatchers.Main).launch {
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

        },
        onClickSearch = {
            editViewModel.search(it)
            focusManager.clearFocus()
        }
    )

    if (dialogState.value) {
        DeleteDialog(
            onDismissRequest = {
                dialogState.value = false
            },
            onConfirm = {
                dialogState.value = false
                editViewModel.delete(
                    id = deleteId.value
                )
            }
        )
    }
}

@Composable
fun BottomButton(
    onClickJump: (Int) -> Unit,
    onClickSearch: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(20.dp),
    ) {

        JumpButton(
            modifier = Modifier
                .weight(1f),
            onClickJump = onClickJump,
        )

        SearchButton(
            modifier = Modifier
                .weight(2f),
            onClick = onClickSearch,
        )
    }
}
