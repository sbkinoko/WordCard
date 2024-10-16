package view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import viewmodel.TestViewModel

@Composable
fun TestScreen(
    modifier: Modifier = Modifier,
    testViewModel: TestViewModel = koinInject(),
) {
    val text = remember { mutableStateOf("") }
    val flag = remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
            ) {
                focusManager.clearFocus()
            },
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Text(
            modifier = Modifier
                .weight(1f),
            text = "出題"
        )
        Text(
            modifier = Modifier
                .weight(1f),
            text = "回答"
        )
        Row(
            modifier = Modifier
                .weight(1f)
        ) {
            TextField(
                modifier = Modifier
                    .weight(3f)
                    .fillMaxHeight(),
                value = text.value,
                onValueChange = {
                    text.value = it
                }
            )
            Button(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                onClick = {
                    println(text.value)
                }
            ) {
                Text("一部表示")
            }
        }
        if (flag.value) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    flag.value = flag.value.not()
                }
            ) {
                Text("全部表示")
            }
        } else {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    flag.value = flag.value.not()
                }
            ) {
                Text("次へ")
            }
        }
    }
}
