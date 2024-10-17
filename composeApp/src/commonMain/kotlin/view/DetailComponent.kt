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
    val backColor = remember {
        mutableStateOf(
            detail.color.toColor()
        )
    }

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
                    update(
                        detail.front,
                        detail.back,
                        field.take( colorStringLength),
                    )

                    backColor.value = field.toColor()
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

const val digit = 2
const val ff = 255
const val radix = 16
const val colorStringLength = 6

fun String.toColor(): Color {
    val tokenField = this.take(colorStringLength)
    if (tokenField.length != digit * colorStringLength) {
        return Color(ff, ff, ff)
    }

    val colorValue = tokenField
        .chunked(digit)
        .map { chunked ->
            try {
                chunked.toInt(radix)
            } catch (e: NumberFormatException) {
                ff
            }
        }

    return Color(colorValue[0], colorValue[1], colorValue[2])
}
