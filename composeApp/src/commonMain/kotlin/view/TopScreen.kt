package view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import viewmodel.TopViewModel

@Composable
fun TopScreen(
    modifier: Modifier = Modifier,
    topViewModel: TopViewModel = koinInject()
) {
    val focusManager = LocalFocusManager.current

    val groups = topViewModel.titleFlow.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
            ) {
                focusManager.clearFocus()
            }
            .padding(10.dp),
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
                    text = title.title,
                    onClickDetail = {
                        topViewModel.onClick(
                            title = title,
                        )
                    },
                    onClickDelete = {
                        topViewModel.deleteAt(
                            index
                        )
                    },
                    onEditText = { newTitle ->
                        topViewModel.editTitle(
                            index,
                            newTitle
                        )
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
    }
}
