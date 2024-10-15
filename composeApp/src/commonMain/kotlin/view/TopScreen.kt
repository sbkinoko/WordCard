package view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import viewmodel.TopViewModel

@Composable
fun TopScreen(
    modifier: Modifier = Modifier,
    topViewModel: TopViewModel = koinInject()
) {
    val focusManager = LocalFocusManager.current
    Column(
        modifier = modifier
            .fillMaxSize()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
            ) {
                focusManager.clearFocus()
            }
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        TopComponent(
            text = "Hello, World!",
            onClickDetail = {
                topViewModel.onClick("Hello world")
            }
        )
    }
}
