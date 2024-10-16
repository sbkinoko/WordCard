package view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.unit.dp

@Composable
fun TopComponent(
    text: String,
    onClickDetail: () -> Unit,
    onClickDelete: () -> Unit,
    onEditText: (String) -> Unit,
    saveText: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        TextField(
            modifier = Modifier.onFocusChanged {
                if (it.isFocused.not()) {
                    saveText()
                }
            },
            value = text,
            onValueChange = onEditText,
            maxLines = 1,
        )
        Button(
            onClick = onClickDetail
        ) {
            Text(text = "Detail")
        }

        Button(
            onClick = onClickDelete,
        ) {
            Text("-")
        }
    }
}
