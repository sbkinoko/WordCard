package view

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TopComponent(
    text: String,
    onClickDetail: () -> Unit,
    onClickTest: () -> Unit,
    onClickDelete: () -> Unit,
    onEditText: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
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
        TextField(
            modifier = Modifier.weight(1f),
            value = text,
            onValueChange = onEditText,
        )
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

        Button(
            onClick = onClickDelete,
        ) {
            Text("-")
        }
    }
}
