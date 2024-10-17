package repository.detail

import domain.Detail
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailRepositoryImpl : DetailRepository {
    private val mutableRealmTitleFlow: MutableSharedFlow<List<Detail>> = MutableSharedFlow()
    private val titleFlow: SharedFlow<List<Detail>>
        get() = mutableRealmTitleFlow.asSharedFlow()

    override val detailListState: StateFlow<List<Detail>>
        get() = titleFlow.stateIn(
            scope = CoroutineScope(Dispatchers.Default),
            started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(),
            initialValue = titleList,
        )

    private var titleList: List<Detail> = emptyList()
        set(value) {
            field = value
            CoroutineScope(Dispatchers.Default).launch {
                mutableRealmTitleFlow.emit(value)
            }
        }

    override fun updateAt(
        id: Int,
        front: String,
        back: String,
        color: String,
    ) {
        titleList = titleList.map { detail ->
            if (detail.id == id) {
                detail.copy(
                    front = front,
                    back = back,
                    color = color,
                )
            } else {
                detail
            }
        }.toMutableList()
    }

    override fun add() {
        val list = titleList + Detail()
        titleList = list
    }

    override fun deleteAt(id: Int) {
        titleList = titleList.filter {
            it.id != id
        }
    }
}
