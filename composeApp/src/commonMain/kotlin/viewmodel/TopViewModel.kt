package viewmodel

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import repository.screentype.ScreenTypeRepository

class TopViewModel : KoinComponent {
    private val screenTypeRepository: ScreenTypeRepository by inject()

    fun onClick(text: String) {
        screenTypeRepository.screenType = text
    }
}
