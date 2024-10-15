import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import repository.screentype.ScreenTypeRepository
import repository.screentype.ScreenTypeRepositoryImpl
import view.DetailScreen
import view.TopScreen

@Composable
@Preview
fun App() {
    val screenTypeRepository: ScreenTypeRepository = koinInject()
    val screenState =
        screenTypeRepository.screenTypeFlow.collectAsState(null)
    MaterialTheme {
        if (screenState.value == null) {
            TopScreen()
        } else {
            DetailScreen()
        }
    }
}
