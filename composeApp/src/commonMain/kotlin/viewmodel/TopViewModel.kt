package viewmodel

import domain.ScreenType
import domain.Title
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import repository.screentype.ScreenTypeRepository
import repository.title.TitleRepository

class TopViewModel : KoinComponent {
    private val screenTypeRepository: ScreenTypeRepository by inject()
    private val titleRepository: TitleRepository by inject()

    val titleFlow = titleRepository.titleState

    fun toEdit(title: Title) {
        CoroutineScope(Dispatchers.Default).launch {
            screenTypeRepository.setScreenType(ScreenType.EDIT)
            screenTypeRepository.title = title
        }
    }

    fun toTest(title: Title) {
        CoroutineScope(Dispatchers.Default).launch {
            screenTypeRepository.setScreenType(ScreenType.TEST)
            screenTypeRepository.title = title
        }
    }

    fun addGroup() {
        titleRepository.add()
    }

    fun deleteAt(index: Int) {
        titleRepository.deleteAt(index)
    }

    fun editTitle(
        index: Int,
        title: String,
    ) {
        titleRepository.updateAt(
            index = index,
            title = title,
        )
    }
}
