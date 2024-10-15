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
import androidx.compose.ui.unit.dp

@Composable
fun TopComponent(
    text: String,
    modifier: Modifier = Modifier,
) {
    val textState = remember { mutableStateOf(text) }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        TextField(
            value = textState.value,
            onValueChange = { textState.value = it },
            maxLines = 1,
        )
        Button(
            onClick = {
                // 詳細への遷移処理
            },
        ) {
            Text(text = "Detail")
        }

        Button(
            onClick = {
                //　削除処理
            },
        ) {
            Text("-")
        }
    }
}
