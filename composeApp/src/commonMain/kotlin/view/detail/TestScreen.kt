package view.detail

import androidx.compose.foundation.background
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import viewmodel.detail.TestViewModel

@Composable
fun TestScreen(
    setJumpTarget: (Int) -> Unit,
    modifier: Modifier = Modifier,
    testViewModel: TestViewModel = koinInject(),
) {
    val focusManager = LocalFocusManager.current

    val question = testViewModel.question.collectAsState()
    val showAnswer = testViewModel.showAnswer.collectAsState()
    val answer = testViewModel.answer.collectAsState()
    val input = testViewModel.input.collectAsState()

    LaunchedEffect(Unit) {
        testViewModel.reset()
    }

    setJumpTarget(
        testViewModel.questionId
    )

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
                .weight(1f)
                .fillMaxWidth(),
            text = question.value.front
        )
        Text(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .then(
                    if (showAnswer.value) {
                        Modifier.background(
                            color = question.value.color.toColor()
                        )
                    } else {
                        Modifier
                    }
                ),
            text = if (showAnswer.value) {
                question.value.back
            } else {
                answer.value
            },
        )
        Row(
            modifier = Modifier
                .weight(1f)
        ) {
            TextField(
                modifier = Modifier
                    .weight(3f)
                    .fillMaxHeight(),
                value = input.value,
                onValueChange = {
                    testViewModel.updateInput(
                        text = it,
                    )
                }
            )
            Button(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                onClick = {
                    testViewModel.open()
                }
            ) {
                Text("一部表示")
            }
        }
        if (showAnswer.value) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(5.dp),
            ) {
                Button(
                    modifier = Modifier
                        .weight(1f),
                    onClick = {
                        testViewModel.goOK()
                    }
                ) {
                    Text("わかった")
                }
                Button(
                    modifier = Modifier
                        .weight(1f),
                    onClick = {
                        testViewModel.goNG()
                    }
                ) {
                    Text("わからなかった")
                }
            }
        } else {
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {
                    testViewModel.showAnswer()
                }
            ) {
                Text("全部表示")
            }
        }
    }
}
