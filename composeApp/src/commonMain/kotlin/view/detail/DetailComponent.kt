package view.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import common.commonBorder
import domain.Detail

@Composable
fun DetailComponent(
    index: Int,
    detail: Detail,
    onClickMove: (String) -> Unit,
    update: (front: String, back: String, color: String) -> Unit,
    delete: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val backColor = remember {
        mutableStateOf(
            detail.color.toColor()
        )
    }

    val front = remember {
        mutableStateOf(detail.front)
    }
    val back = remember {
        mutableStateOf(detail.back)
    }
    val color = remember {
        mutableStateOf(detail.color)
    }

    LaunchedEffect(detail) {
        front.value = detail.front
        back.value = detail.back
        color.value = detail.color
        backColor.value = detail.color.toColor()
    }

    Row(
        modifier = modifier
            .border(
                width = 1.dp,
                color = Color.Black,
                shape = RoundedCornerShape(5.dp)
            ).padding(5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Button(
                onClick = {
                    // 上に追加処理
                },
            ) {
                Text("+")
            }

            Text(
                text = index.toString()
            )

            Button(
                onClick = {
                    // 下に追加処理
                },
            )
            {
                Text("+")
            }
        }

        Column(
            modifier = Modifier
                .wrapContentHeight()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            TextField(
                modifier = Modifier
                    .background(
                        color = backColor.value
                    ).onFocusChanged {
                        if (it.isFocused) {
                            return@onFocusChanged
                        }

                        update(
                            detail.front,
                            detail.back,
                            color.value,
                        )
                    },
                label = { Text("Color") },
                value = color.value,
                onValueChange = { field ->
                    color.value = field.take(colorStringLength)
                    backColor.value = field.toColor()
                },
            )

            TextField(
                modifier = Modifier
                    .onFocusChanged {
                        if (it.isFocused) {
                            return@onFocusChanged
                        }

                        update(
                            front.value,
                            detail.back,
                            detail.color,
                        )
                    }.onKeyEvent {
                        //　改行したらイベントを消費して何も起こさない
                        it.key.keyCode == Key.Enter.keyCode
                    },
                label = { Text("表") },
                value = front.value,
                onValueChange = {
                    front.value = it
                },
            )

            TextField(
                modifier = Modifier
                    .onFocusChanged {
                        if (it.isFocused) {
                            return@onFocusChanged
                        }

                        update(
                            detail.front,
                            back.value,
                            detail.color
                        )
                    }.onKeyEvent {
                        //　改行したらイベントを消費して何も起こさない
                        it.key.keyCode == Key.Enter.keyCode
                    },
                label = { Text("裏") },
                value = back.value,
                onValueChange = {
                    back.value = it
                },
            )
        }

        RightEditArea(
            modifier = Modifier
                .width(100.dp),
            onClickMove = onClickMove,
            delete = delete,
        )
    }
}

@Composable
fun RightEditArea(
    onClickMove: (String) -> Unit = {},
    delete: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val moveTo = remember {
        mutableStateOf("")
    }

    Column(
        modifier = modifier.padding(5.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .commonBorder(),
            label = { Text("移動先") },
            value = moveTo.value,
            onValueChange = {
                moveTo.value = it
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
        )

        Button(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = {
                onClickMove(moveTo.value)
                moveTo.value = ""
            },
        ) {
            Text("Move")
        }

        Button(
            modifier = Modifier
                .fillMaxWidth(),
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
    if (tokenField.length != colorStringLength) {
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
