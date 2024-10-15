package viewmodel

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import repository.screentype.ScreenTypeRepository

class DetailViewModel : KoinComponent {
    private val screenTypeRepository: ScreenTypeRepository by inject()

    fun reset() {
        screenTypeRepository.screenType = null
    }
}
