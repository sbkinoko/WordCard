package view

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import view.detail.TestScreen
import viewmodel.detail.TestViewModel

@Preview
@Composable
fun TestScreenPreview() {
    TestScreen(
        testViewModel = TestViewModel(),
        setJumpTarget = {}
    )
}
