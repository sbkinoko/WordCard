package viewmodel

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import repository.detail.DetailRepository
import repository.screentype.ScreenTypeRepository

class DetailViewModel : KoinComponent {
    private val screenTypeRepository: ScreenTypeRepository by inject()

    private val detailRepository: DetailRepository by inject()

    val detailListState = detailRepository.detailListState

    fun reset() {
        screenTypeRepository.screenType = null
    }
}
