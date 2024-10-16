package viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.stateIn
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import repository.screentype.ScreenTypeRepository
import repository.title.TitleRepository

class TopViewModel : KoinComponent {
    private val screenTypeRepository: ScreenTypeRepository by inject()
    private val titleRepository: TitleRepository by inject()

    val titleFlow = titleRepository.titleFlow.stateIn(
        scope = CoroutineScope(Dispatchers.Default),
        started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(),
        initialValue = titleRepository.titleList,
    )

    fun onClick(text: String) {
        screenTypeRepository.screenType = text
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
