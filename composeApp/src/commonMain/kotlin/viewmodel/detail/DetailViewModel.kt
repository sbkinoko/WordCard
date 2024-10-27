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
import repository.detailorder.DetailOrderRepository
import repository.screentype.ScreenTypeRepository

class DetailViewModel : KoinComponent {
    private val screenTypeRepository: ScreenTypeRepository by inject()

    private val detailRepository: DetailRepository by inject()
    private val detailOrderRepository: DetailOrderRepository by inject()

    val detailListState: StateFlow<List<ObjectId>> =
        detailOrderRepository.detailOrderState

    fun getItem(id: ObjectId): Detail {
        return detailRepository.getDetail(id)
    }

    private val mutableTitleFlow: MutableStateFlow<String> = MutableStateFlow("")
    val titleFlow: StateFlow<String> = mutableTitleFlow.asStateFlow()

    val screenType =
        screenTypeRepository.screenType


    private var isFirst: Boolean = false

    private val needInit: Boolean
        get() = detailOrderRepository.isLoading.not() &&
                detailRepository.isLoading.not() &&
                detailOrderRepository.isEmpty &&
                isFirst


    init {
        CoroutineScope(Dispatchers.Default).launch {
            detailListState.collect {
                tryInitOrder()
            }
        }
        CoroutineScope(Dispatchers.Default).launch {
            detailRepository.detailListState.collect {
                tryInitOrder()
            }
        }
    }

    private fun tryInitOrder() {
        if (needInit) {
            val list = detailRepository.list.map {
                it.id
            }
            detailOrderRepository.update(
                id = screenTypeRepository.title!!.id,
                list = list
            )
            isFirst = false
        }
    }

    fun reset() {
        screenTypeRepository.title = null
        mutableTitleFlow.value = ""
    }

    fun setId() {
        isFirst = true
        detailRepository.titleId = screenTypeRepository.title!!.id
        mutableTitleFlow.value = screenTypeRepository.title!!.title
        detailOrderRepository.titleId = screenTypeRepository.title!!.id
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

    fun move(
        id: ObjectId,
        index: String,
    ) {
        val indexNum = index.toIntOrNull() ?: return
        val list = detailOrderRepository.list
        if (indexNum < 0 || indexNum >= list.size) {
            return
        }
        val newList = list.filter {
            it != id
        }.toMutableList()
        newList.add(indexNum, id)
        detailOrderRepository.update(
            id = id,
            list = newList
        )
    }
}
