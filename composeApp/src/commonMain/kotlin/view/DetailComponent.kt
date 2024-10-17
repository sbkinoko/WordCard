package view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import domain.Detail

@Composable
fun DetailComponent(
    detail: Detail,
    update: (front: String, back: String, color: String) -> Unit,
    delete: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val backColor = remember { mutableStateOf(Color.White) }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    // 上に追加処理
                },
            ) {
                Text("+")
            }
            Button(
                onClick = {
                    // 下に追加処理
                },
            )
            {
                Text("+")

            }
        }
        TextField(
            modifier = Modifier.weight(1f),
            value = detail.front,
            onValueChange = {
                update(
                    it,
                    detail.back,
                    detail.color
                )
            },
            label = { Text("表") }
        )
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = detail.color,
                onValueChange = { field ->
                    val tokenField = field.take(6)
                    update(
                        detail.front,
                        detail.back,
                        tokenField,
                    )

                    if (tokenField.length != 6) {
                        return@TextField
                    }
                    val colorValue = tokenField
                        .chunked(2)
                        .map { chunked ->
                            try {
                                chunked.toInt(16)
                            } catch (e: NumberFormatException) {
                                255
                            }
                        }
                    backColor.value = Color(colorValue[0], colorValue[1], colorValue[2])
                },
                label = { Text("Color") }
            )

            TextField(
                value = detail.back,
                onValueChange = {
                    update(
                        detail.front,
                        it,
                        detail.color
                    )
                },
                label = { Text("裏") },
                modifier = Modifier.background(
                    color = backColor.value
                )
            )
        }

        Button(
            onClick = delete,
        ) {
            Text("-")
        }
    }
}
