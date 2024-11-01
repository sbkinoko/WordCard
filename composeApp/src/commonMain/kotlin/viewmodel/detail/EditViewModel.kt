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
import usecase.getorder.GetItemIndexUseCase
import usecase.moveitem.MoveItemUseCase

class EditViewModel : KoinComponent {

    private val detailRepository: DetailRepository by inject()

    private val addItemUseCase: AddItemUseCase by inject()
    private val deleteItemUseCase: DeleteItemUseCase by inject()
    private val moveItemUseCase: MoveItemUseCase by inject()
    private val getIOrderedItemUseCase: GetIOrderedItemUseCase by inject()
    private val getItemIndexUseCase: GetItemIndexUseCase by inject()

    private val mutableDetailOrderState: MutableStateFlow<List<Detail>> =
        MutableStateFlow(emptyList())

    val detailOrderState: StateFlow<List<Detail>>
        get() = mutableDetailOrderState.asStateFlow()

    fun init() {
        updateState()
    }

    private var allList: List<Detail> = emptyList()
    private var filterText: String = ""

    private val filteredList: List<Detail>
        get() {
            return allList.filter {
                // 指定した文字を含む
                it.front.contains(filterText) ||
                        it.back.contains(filterText) ||
                        it.color.contains(filterText) ||
                        //　空
                        (it.front.isEmpty() &&
                                it.back.isEmpty() &&
                                it.color.isEmpty())
            }
        }

    private fun updateState() {
        CoroutineScope(Dispatchers.Default).launch {
            allList = getIOrderedItemUseCase.invoke()

            mutableDetailOrderState.emit(
                filteredList,
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
        position: Int,
    ) {
        val detail = filteredList[index]
        val insertPosition = allList.indexOf(detail) + position

        val id = ObjectId()
        addItemUseCase.invoke(objectId = id)
        moveItemUseCase.invoke(
            id = id,
            index = insertPosition.toString(),
        )
        updateState()
    }

    fun insertAtLast() {
        val id = ObjectId()
        addItemUseCase.invoke(objectId = id)
        updateState()
    }

    fun getIndexOf(
        id: ObjectId,
    ): Int {
        return getItemIndexUseCase(objectId = id)
    }

    fun getActualIndex(id: ObjectId): Int {

        return allList.indexOfFirst {
            it.id == id
        }
    }

    fun search(
        text: String,
    ) {
        filterText = text
        updateState()
    }
}
