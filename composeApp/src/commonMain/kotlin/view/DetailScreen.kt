package view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import domain.ScreenType
import kotlinx.coroutines.delay
import org.koin.compose.koinInject
import viewmodel.DetailViewModel

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    detailViewModel: DetailViewModel = koinInject(),
) {
    val focusManager = LocalFocusManager.current
    val screenType = detailViewModel.screenType.collectAsState()
    val title = detailViewModel.titleFlow.collectAsState()


    val flag = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        detailViewModel.setId()
        delay(
            timeMillis = 100,
        )
        flag.value = true
    }

    Column(
        modifier = modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
            ) {
                focusManager.clearFocus()
            }
            .padding(10.dp)
    ) {
        if (flag.value) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 5.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = title.value
                )
            }
            if (screenType.value == ScreenType.EDIT) {
                EditScreen(
                    modifier = Modifier
                        .weight(1f),
                )
            } else if (screenType.value == ScreenType.TEST) {
                TestScreen(
                    modifier = Modifier
                        .weight(1f),
                )
            }
        } else {
            Spacer(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(
                        color = Color(
                            alpha = 80,
                            red = 0,
                            green = 0,
                            blue = 0,
                        )
                    )
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Button(
                modifier = Modifier
                    .padding(horizontal = 10.dp),
                onClick = {
                    detailViewModel.reset()
                },
            ) {
                Text(
                    "←"
                )
            }

            when (screenType.value) {
                ScreenType.EDIT -> {
                    Button(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 10.dp),
                        onClick = {
                            detailViewModel.toTest()
                        },
                    ) {
                        Text(
                            "Test"
                        )
                    }
                }

                ScreenType.TEST -> {
                    Button(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 10.dp),
                        onClick = {
                            detailViewModel.toEdit()
                        }
                    ) {
                        Text(
                            "Edit"
                        )
                    }
                }
            }
        }
    }
}
