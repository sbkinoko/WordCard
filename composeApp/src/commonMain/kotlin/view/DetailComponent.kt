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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun DetailComponent(
    modifier: Modifier = Modifier,
) {
    val frontText = remember { mutableStateOf("") }
    val backText = remember { mutableStateOf("") }
    val backColorString = remember { mutableStateOf("") }
    val backColor: MutableState<Color> = remember { mutableStateOf(Color(255, 255, 255)) }
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
            value = frontText.value,
            onValueChange = { frontText.value = it },
            label = { Text("表") }
        )
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = backColorString.value,
                onValueChange = {
                    backColorString.value = it.take(6)
                    if (backColorString.value.length != 6) {
                        return@TextField
                    }
                    val colorValue = backColorString.value
                        .chunked(2)
                        .map {
                            it.toInt(16)
                        }
                    backColor.value = Color(colorValue[0], colorValue[1], colorValue[2])
                },
                label = { Text("Color") }
            )

            TextField(
                value = backText.value,
                onValueChange = { backText.value = it },
                label = { Text("裏") },
                modifier = Modifier.background(
                    color = backColor.value
                )
            )
        }

        Button(
            onClick = {
                // 削除処理
            },
        ) {
            Text("-")
        }
    }
}
