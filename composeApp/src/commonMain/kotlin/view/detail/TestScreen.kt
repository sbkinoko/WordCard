package view.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import common.commonBorder
import common.componentBackground
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
                .weight(2f)
                .fillMaxWidth()
                .commonBorder()
                .componentBackground(),
            text = question.value.front
        )

        ColorArea(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            color = if (
                showAnswer.value
            ) {
                question.value.color.toColor()
            } else {
                Color.White
            }
        )

        Text(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .commonBorder()
                .componentBackground(),
            text = if (showAnswer.value) {
                question.value.back
            } else {
                answer.value
            },
        )

        Row(
            modifier = Modifier
                .weight(2f)
                .commonBorder()
                .componentBackground()
                .padding(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
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

@Composable
fun ColorArea(
    color: Color,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(5.dp),
    ) {

        Spacer(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .commonBorder()
                .background(
                    color = color,
                    RoundedCornerShape(5.dp)
                )
        )
        Spacer(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .commonBorder(
                    color = Color.White,
                )
                .background(
                    color = color,
                    shape = RoundedCornerShape(5.dp)
                )
        )
    }
}
