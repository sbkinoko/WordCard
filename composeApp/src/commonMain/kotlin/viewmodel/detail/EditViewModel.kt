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
import usecase.additem.AddItemUseCase
import usecase.deleteitem.DeleteItemUseCase
import usecase.getitemorder.GetIOrderedItemUseCase
import usecase.moveitem.MoveItemUseCase

class EditViewModel : KoinComponent {

    private val detailRepository: DetailRepository by inject()

    private val addItemUseCase: AddItemUseCase by inject()
    private val deleteItemUseCase: DeleteItemUseCase by inject()
    private val moveItemUseCase: MoveItemUseCase by inject()
    private val getIOrderedItemUseCase: GetIOrderedItemUseCase by inject()

    private val mutableDetailOrderState: MutableStateFlow<List<Detail>> =
        MutableStateFlow(emptyList())

    val detailOrderState: StateFlow<List<Detail>>
        get() = mutableDetailOrderState.asStateFlow()

    init {
        CoroutineScope(Dispatchers.Default).launch {
            mutableDetailOrderState.collect {
                println(it)
            }
        }
    }

    fun init() {
        updateState()
    }

    private fun updateState() {
        CoroutineScope(Dispatchers.Default).launch {
            mutableDetailOrderState.emit(
                getIOrderedItemUseCase.invoke()
            )
        }
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
        updateState()
    }

    fun delete(id: ObjectId) {
        deleteItemUseCase.invoke(
            id = id,
        )
        updateState()
    }

    fun move(
        id: ObjectId,
        index: String,
    ) {
        moveItemUseCase.invoke(
            id = id,
            index = index,
        )
        updateState()
    }

    fun insertAt(
        index: Int,
    ) {
        val id = ObjectId()
        addItemUseCase.invoke(objectId = id)
        moveItemUseCase.invoke(
            id = id,
            index = index.toString(),
        )
        updateState()
    }
}
