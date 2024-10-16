package view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TopComponent(
    text: String,
    onClickDetail: () -> Unit,
    onClickDelete: () -> Unit,
    onEditText: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        TextField(
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
