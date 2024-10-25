package viewmodel.detail

import domain.Detail
import domain.ScreenType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.mongodb.kbson.ObjectId
import repository.detail.DetailRepository
import repository.screentype.ScreenTypeRepository

class DetailViewModel : KoinComponent {
    private val screenTypeRepository: ScreenTypeRepository by inject()

    private val detailRepository: DetailRepository by inject()

    var list: List<ObjectId> = listOf()

    val detailListState: MutableStateFlow<List<ObjectId>> = MutableStateFlow(
        listOf()
    )

    init {
        CoroutineScope(Dispatchers.Default).launch {
            detailRepository.detailListState.collect {
                list = it.map { detail ->
                    detail.id
                }
                detailListState.value = list
            }
        }
    }

    fun getItem(id: ObjectId): Detail {
        return detailRepository.getDetail(id)
    }

    private val mutableTitleFlow: MutableStateFlow<String> = MutableStateFlow("")
    val titleFlow: StateFlow<String> = mutableTitleFlow.asStateFlow()

    val screenType =
        screenTypeRepository.screenType

    fun reset() {
        screenTypeRepository.title = null
        mutableTitleFlow.value = ""
    }

    fun setId() {
        detailRepository.titleId = screenTypeRepository.title!!.id
        mutableTitleFlow.value = screenTypeRepository.title!!.title
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
            titleId = screenTypeRepository.title!!.id,
        )
    }

    fun delete(id: ObjectId) {
        detailRepository.deleteAt(id)
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
