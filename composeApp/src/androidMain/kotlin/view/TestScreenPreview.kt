package view

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import viewmodel.TestViewModel

@Preview
@Composable
fun TestScreenPreview() {
    TestScreen(
        testViewModel = TestViewModel(),
        setJumpTarget = {}
    )
}
