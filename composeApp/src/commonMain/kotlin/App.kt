import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import repository.screentype.ScreenTypeRepository
import view.detail.DetailScreen
import view.top.TopScreen

@Composable
@Preview
fun App() {
    val screenTypeRepository: ScreenTypeRepository = koinInject()
    val title =
        screenTypeRepository.titleFlow.collectAsState(null)
    MaterialTheme {
        if (title.value == null) {
            TopScreen()
        } else {
            DetailScreen()
        }
    }
}
