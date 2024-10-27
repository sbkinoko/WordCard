package viewmodel.detail

import domain.ScreenType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import repository.screentype.ScreenTypeRepository

class DetailViewModel : KoinComponent {
    private val screenTypeRepository: ScreenTypeRepository by inject()

    private val mutableTitleFlow: MutableStateFlow<String> = MutableStateFlow("")
    val titleFlow: StateFlow<String> = mutableTitleFlow.asStateFlow()

    val screenType =
        screenTypeRepository.screenType

    fun reset() {
        screenTypeRepository.title = null
        mutableTitleFlow.value = ""
    }

    fun init() {
        mutableTitleFlow.value = screenTypeRepository.title!!.title
    }


    fun toTest() {
        CoroutineScope(Dispatchers.Default).launch {
            screenTypeRepository.setScreenType(ScreenType.TEST)
        }
    }

    fun toEdit() {
        CoroutineScope(Dispatchers.Default).launch {
            screenTypeRepository.setScreenType(ScreenType.EDIT)
        }
    }
}
