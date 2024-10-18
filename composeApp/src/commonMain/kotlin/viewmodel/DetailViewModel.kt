package viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.mongodb.kbson.ObjectId
import repository.detail.DetailRepository
import repository.screentype.ScreenTypeRepository

class DetailViewModel : KoinComponent {
    private val screenTypeRepository: ScreenTypeRepository by inject()

    private val detailRepository: DetailRepository by inject()

    val detailListState = detailRepository.detailListState

    private val mutableTitleFlow: MutableStateFlow<String> = MutableStateFlow("")
    val titleFlow: StateFlow<String> = mutableTitleFlow.asStateFlow()

    fun reset() {
        screenTypeRepository.screenType = null
        mutableTitleFlow.value = ""
    }

    fun setId() {
        detailRepository.titleId = screenTypeRepository.screenType!!.id
        mutableTitleFlow.value = screenTypeRepository.screenType!!.title
    }

    fun update(
        id: ObjectId,
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
        detailRepository.add(
            titleId = screenTypeRepository.screenType!!.id,
        )
    }

    fun delete(id: ObjectId) {
        detailRepository.deleteAt(id)
    }
}
