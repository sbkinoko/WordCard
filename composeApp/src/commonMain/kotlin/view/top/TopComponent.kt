package view.top

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import domain.Title
import view.detail.RightEditArea

@Composable
fun TopComponent(
    isEditable: Boolean,
    index: Int,
    title: Title,
    onClickDetail: () -> Unit,
    onClickTest: () -> Unit,
    onEditText: (String) -> Unit,
    delete: () -> Unit,
    onClickMove: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val titleText = remember {
        mutableStateOf(title.title)
    }

    LaunchedEffect(title) {
        titleText.value = title.title
    }

    Row(
        modifier = modifier
            .border(
                width = 1.dp,
                color = Color.Black,
                shape = RoundedCornerShape(5.dp)
            )
            .padding(5.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (isEditable) {
            Text(text = index.toString())
        }
        TextField(
            modifier = Modifier
                .weight(1f)
                .onFocusChanged {
                    if (it.isFocused) {
                        return@onFocusChanged
                    }
                    onEditText(
                        titleText.value
                    )
                },
            value = titleText.value,
            onValueChange = {
                titleText.value = it
            },
        )

        if (isEditable) {
            RightEditArea(
                modifier = Modifier
                    .width(100.dp),
                onClickMove = onClickMove,
                delete = delete,
            )
        } else {
            Button(
                onClick = onClickDetail
            ) {
                Text(text = "Edit")
            }

            Button(
                onClick = onClickTest,
            ) {
                Text(text = "Test")
            }
        }
    }
}
