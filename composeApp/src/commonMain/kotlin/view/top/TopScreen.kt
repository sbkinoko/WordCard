package view.top

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import org.mongodb.kbson.ObjectId
import view.dialog.DeleteDialog
import viewmodel.top.TopViewModel

@Composable
fun TopScreen(
    modifier: Modifier = Modifier,
    topViewModel: TopViewModel = koinInject()
) {
    val focusManager = LocalFocusManager.current

    val groups = topViewModel.titleOrderState.collectAsState()

    val dialogState = remember {
        mutableStateOf(false)
    }
    val deleteId = remember {
        mutableStateOf(ObjectId())
    }

    val isEditable = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        topViewModel.init()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .clickable(
                interactionSource = remember {
                    MutableInteractionSource()
                },
                indication = null,
            ) {
                focusManager.clearFocus()
            }
            .padding(10.dp)
            .imePadding()
            .safeDrawingPadding(),
        verticalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(5.dp),
        ) {
            itemsIndexed(
                groups.value
            ) { index, title ->
                TopComponent(
                    index = index,
                    title = title,
                    isEditable = isEditable.value,
                    onClickDetail = {
                        topViewModel.toEdit(
                            title = title,
                        )
                    },
                    onClickTest = {
                        topViewModel.toTest(
                            title = title,
                        )
                    },
                    onEditText = { newTitle ->
                        topViewModel.editTitle(
                            title.id,
                            newTitle
                        )
                    },
                    delete = {
                        dialogState.value = true
                        deleteId.value = title.id
                    },
                    onClickMove = { moveTo ->
                        topViewModel.moveTitle(
                            id = title.id,
                            index = moveTo
                        )
                        focusManager.clearFocus()
                    }
                )
            }
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                topViewModel.addGroup()
            }
        ) {
            Text("+")
        }

        EditCheckBox(
            isEditable = isEditable.value,
            onCheckChanged = {
                isEditable.value = it
            }
        )
    }

    if (dialogState.value) {
        DeleteDialog(
            onDismissRequest = {
                dialogState.value = false
            },
            onConfirm = {
                dialogState.value = false
                topViewModel.delete(
                    id = deleteId.value
                )
            }
        )
    }
}

@Composable
private fun EditCheckBox(
    isEditable: Boolean,
    onCheckChanged: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            checked = isEditable,
            onCheckedChange = onCheckChanged,
        )
        Text(
            text = "編集モード",
        )
    }
}
