package viewmodel.top

import domain.ScreenType
import domain.Title
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.mongodb.kbson.ObjectId
import repository.screentype.ScreenTypeRepository
import repository.title.TitleRepository
import repository.titleorder.TitleOrderRepository
import usecase.deletetitle.DeleteTitleUseCase
import usecase.movetitle.MoveTitleUseCase

class TopViewModel : KoinComponent {
    private val screenTypeRepository: ScreenTypeRepository by inject()
    private val titleRepository: TitleRepository by inject()
    private val titleOrderRepository: TitleOrderRepository by inject()

    private val deleteTitleUseCase: DeleteTitleUseCase by inject()
    private val moveTitleUseCase: MoveTitleUseCase by inject()

    private val mutableTitleOrderState: MutableStateFlow<List<Title>> =
        MutableStateFlow(emptyList())

    val titleOrderState: StateFlow<List<Title>>
        get() = mutableTitleOrderState.asStateFlow()

    fun init() {
        update()
    }

    fun toEdit(title: Title) {
        CoroutineScope(Dispatchers.Default).launch {
            screenTypeRepository.setScreenType(ScreenType.EDIT)
            screenTypeRepository.title = title
        }
    }

    fun toTest(title: Title) {
        CoroutineScope(Dispatchers.Default).launch {
            screenTypeRepository.setScreenType(ScreenType.TEST)
            screenTypeRepository.title = title
        }
    }

    fun addGroup() {
        val objectId = ObjectId()
        titleRepository.add(objectId)
        titleOrderRepository.update(
            newList = titleOrderRepository.getItemOrder() + objectId
        )
        update()
    }

    fun delete(id: ObjectId) {
        deleteTitleUseCase.invoke(
            titleId = id,
        )
        update()
    }

    fun editTitle(
        objectId: ObjectId,
        title: String,
    ) {
        titleRepository.updateAt(
            id = objectId,
            title = title,
        )
        update()
    }

    fun moveTitle(
        id: ObjectId,
        index: String,
    ) {
        moveTitleUseCase.invoke(
            id = id,
            index = index,
        )
        update()
    }

    private fun update() {
        CoroutineScope(Dispatchers.Default).launch {
            val list = titleOrderRepository.getItemOrder()
            val orderedList = list.mapNotNull {
                titleRepository.get(it)
            }
            mutableTitleOrderState.emit(
                orderedList,
            )
        }
    }
}
