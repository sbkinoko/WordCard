package view.detail.edit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun JumpButton(
    onClickJump: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val idString = remember {
        mutableStateOf("")
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            modifier = Modifier
                .weight(1f),
            value = idString.value,
            onValueChange = { it ->
                idString.value = it
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
        )

        Button(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = {
                val num = idString.value.toIntOrNull()
                idString.value = ""
                if (num == null)
                    return@Button
                onClickJump(num)
            }
        ) {
            Text(text = "JUMP")
        }
    }
}
