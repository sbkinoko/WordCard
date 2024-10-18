package viewmodel

import domain.Title
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import repository.screentype.ScreenTypeRepository
import repository.title.TitleRepository

class TopViewModel : KoinComponent {
    private val screenTypeRepository: ScreenTypeRepository by inject()
    private val titleRepository: TitleRepository by inject()

    val titleFlow = titleRepository.titleState

    fun onClick(title: Title) {
        screenTypeRepository.screenType = title
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
