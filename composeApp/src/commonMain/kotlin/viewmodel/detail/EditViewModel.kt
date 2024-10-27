package viewmodel.detail

import domain.Detail
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
import usecase.additem.AddItemUseCase
import usecase.deleteitem.DeleteItemUseCase
import usecase.moveitem.MoveItemUseCase

class EditViewModel : KoinComponent {

    private val detailRepository: DetailRepository by inject()
    private val detailOrderRepository: DetailOrderRepository by inject()
    private val screenTypeRepository: ScreenTypeRepository by inject()

    private val addItemUseCase: AddItemUseCase by inject()
    private val deleteItemUseCase: DeleteItemUseCase by inject()
    private val moveItemUseCase: MoveItemUseCase by inject()

    private val mutableDetailOrderState: MutableStateFlow<List<ObjectId>> =
        MutableStateFlow(emptyList())

    val detailOrderState: StateFlow<List<ObjectId>>
        get() = mutableDetailOrderState.asStateFlow()

    private var isFirst: Boolean = false

    private val titleId: ObjectId
        get() = screenTypeRepository.title!!.id

    init {
        CoroutineScope(Dispatchers.Default).launch {
            detailRepository.detailListState.collect {
                tryInitOrder()
            }
        }
    }

    fun init() {
        isFirst = true
    }

    private fun tryInitOrder() {
        if (needInit.not()) {
            return
        }

        val list = detailRepository.list.map {
            it.id
        }
        detailOrderRepository.update(
            titleId = titleId,
            list = list
        )
        isFirst = false
    }

    private val needInit: Boolean
        get() = detailOrderRepository.isLoading.not() &&
                detailRepository.isLoading.not() &&
                detailOrderRepository.isEmpty &&
                isFirst

    fun getItem(id: ObjectId): Detail? {
        return detailRepository.getDetail(id)
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
        addItemUseCase.invoke()
    }

    fun delete(id: ObjectId) {
        deleteItemUseCase.invoke(id = id)
    }

    fun move(
        id: ObjectId,
        index: String,
    ) {
        moveItemUseCase.invoke(
            id = id,
            index = index,
        )
    }
}
