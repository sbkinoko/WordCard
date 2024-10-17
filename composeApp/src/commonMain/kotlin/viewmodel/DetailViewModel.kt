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

    fun update(
        id: Int,
        front: String,
        back: String,
        color: String,
    ) {
        detailRepository.updateAt(
            id = id,
            front = front,
            back = back,
            color = color,
        )
    }

    fun add() {
        detailRepository.add()
    }

    fun delete(id: Int) {
        detailRepository.deleteAt(id)
    }
}
